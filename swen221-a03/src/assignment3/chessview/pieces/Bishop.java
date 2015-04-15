package assignment3.chessview.pieces;

import assignment3.chessview.*;

/**
 * The bishop can move any number of squares diagonally, but may not leap over other pieces.
 * 
 * @author diego
 *
 */
public class Bishop extends PieceImpl implements Piece {

	/**
	 * {@inheritDocs}
	 */

	public Bishop(boolean isWhite) {
		super(isWhite);
	}

	public boolean isValidMove(Position oldPosition, Position newPosition, Piece isTaken, Board board) {

		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);

		// Check if the Rook is moving horizontal or vertical
		int shiftX = Math.abs(newPosition.column() - oldPosition.column());
		int shiftY = Math.abs(newPosition.row() - oldPosition.row());

		if (this.equals(p)) { // make sure this is the right cell
			if (shiftX == shiftY) { // check if moving diagonally
				if (board.clearDiaganolExcept(oldPosition, newPosition, p, t)) { // check if there is a clear (diagonal) path
					if (isTaken != null && t != null) { // if the landing position is taken
						if (isTaken.equals(t)) {
							if (this.isWhite && !t.isWhite() || !this.isWhite && t.isWhite()) { // check that is not taking over the same seed
								return true;
							}
						}
					} else if (t == null) { // if the landing position is free
						return true;
					}
				}
			}
		}
		return false;
	}

	public String toString() {
		if (isWhite) {
			return "B";
		} else {
			return "b";
		}
	}
}
