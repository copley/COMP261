package assignment3.chessview.pieces;

import assignment3.chessview.*;

/**
 * A king can move one square in any direction (horizontally, vertically, or diagonally) unless the square is already occupied by a friendly piece or
 * the move would place the king in check. As a result, the opposing kings may never occupy adjacent squares (see opposition), but the king can give
 * discovered check by unmasking a bishop, rook, or queen. The king is also involved in the special move of castling.
 * 
 * @author diego
 *
 */

public class King extends PieceImpl implements Piece {
		
	public King(boolean isWhite) {
		super(isWhite);
	}
	
	public boolean isValidMove(Position oldPosition, Position newPosition, Piece isTaken, Board board) {
		
		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);

		// Check if the King is moving horizontal or vertical
		int shiftX = Math.abs(newPosition.column() - oldPosition.column());
		int shiftY = Math.abs(newPosition.row() - oldPosition.row());

		// KING IS ON A VALID MOVE ONLY IF HIS NEXT MOVE IS NOT PLACING HIM IN CHECK POSITION (DONE IN SINGLEPIECEMOVE AND SINGLEPIECETAKE)
		if (this.equals(p)) { // make sure this is the right cell
			if (isTaken != null && t != null) { // if the landing position is taken
				if (isTaken.equals(t)) {
					if (this.isWhite && !t.isWhite() || !this.isWhite && t.isWhite()) { // check that is not taking over the same seed
						if (shiftY == 1  && shiftX == 0) { // check if moving horizontally
							return true;
						} else if (shiftX == 1 && shiftY == 0) { // check if is moving vertically
							return true;
						} else if (shiftX == shiftY && shiftY == 1) { // check if is moving diagonally
							return true;
						}
					}
				}
			} else if (t == null) { // if the landing position is free
				if (shiftY == 1  && shiftX == 0) { // check if moving horizontally
					if (!isOnCheck(newPosition, board)) {
						return true;
					}
				} else if (shiftX == 1 && shiftY == 0) { // check if is moving vertically
					if (!isOnCheck(newPosition, board)) {
						return true;
					}
				} else if (shiftX == shiftY && shiftY == 1) { // check if is moving diagonally
					if (!isOnCheck(newPosition, board)) {
						return true;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * checks if the new position would put the king in check mode. Used for castling. Doesn't check if the new position is occupied or not. 
	 * @param kingNewPosition the position to be checked
	 * @param board the current game board
	 * @return true if is on check, false is is not on check
	 */
	public boolean isOnCheck(Position kingNewPosition, Board board) {
			// Check through opposition pieces to see whether they can take king on nextMove
			Position kingNewPos = kingNewPosition;
			for (int row = 1; row <= 8; ++row) {
				for (int col = 1; col <= 8; ++col) {
					Position pos = new Position(row, col);
					Piece p = board.pieceAt(pos);
					// If this is an opposition piece, and it can take my king,
					// then we're definitely in check.
					if (p != null && p.isWhite() != this.isWhite() && p.isValidMove(pos, kingNewPos, p, board)) {
						// p can take opposition king, so we're in check.	
						return true;
					}
				}
			}
			return false;
		}
	
	public String toString() {
		if (isWhite) {
			return "K";
		} else {
			return "k";
		}
	}
}