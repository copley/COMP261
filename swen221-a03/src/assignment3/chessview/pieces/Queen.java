package assignment3.chessview.pieces;

import assignment3.chessview.*;

/**
 * The queen combines the power of the rook and bishop and can move any number of squares along rank, file, or diagonal, but it may not leap over
 * other pieces.
 * 
 * @author diego
 */

public class Queen extends PieceImpl implements Piece {

	/**
	 * {@inheritDocs}
	 */

	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public boolean isValidMove(Position oldPosition, Position newPosition, Piece isTaken, Board board) {
		
		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);

		// Check if the Queen is moving horizontal or vertical
		int shiftX = Math.abs(newPosition.column() - oldPosition.column());
		int shiftY = Math.abs(newPosition.row() - oldPosition.row());

		if (this.equals(p)) { // make sure this is the right cell
			if (isTaken != null && t != null) { // if the landing position is taken
				if (isTaken.equals(t)) {
					if (this.isWhite && !t.isWhite() || !this.isWhite && t.isWhite()) { // check that is not taking over the same seed
						if (shiftY > 0 && shiftX == 0) { // check if moving horizontally
							if (board.clearColumnExcept(oldPosition, newPosition, p, t)) { // check if there is a clear (row) path
								return true;
							}
						} else if (shiftX > 0 && shiftY == 0) { // check if is moving vertically
							if (board.clearRowExcept(oldPosition, newPosition, p, t)) { // check if there is a clear (col) path
								return true;
							}
						} else if (shiftX == shiftY) { // check if is moving diagonally
							if (board.clearDiaganolExcept(oldPosition, newPosition, p, t)) { // check if there is a clear (diagonal) path
								return true;
							}
						}
					}
				}
			} else if (t == null) { // if the landing position is free
				if (shiftY > 0 && shiftX == 0) { // check if moving horizontally
					if (board.clearColumnExcept(oldPosition, newPosition, p, t)) { // check if there is a clear (row) path
						return true;
					}
				} else if (shiftX > 0 && shiftY == 0) { // check if is moving vertically
					if (board.clearRowExcept(oldPosition, newPosition, p, t)) { // check if there is a clear (col) path
						return true;
					}
				} else if (shiftX == shiftY) { // check if is moving diagonally
					if (board.clearDiaganolExcept(oldPosition, newPosition, p, t)) { // check if there is a clear (diagonal) path
						return true;
					}
				}
			}
		}
		return false;
	}

	public String toString() {
		if (isWhite) {
			return "Q";
		} else {
			return "q";
		}
	}
}
