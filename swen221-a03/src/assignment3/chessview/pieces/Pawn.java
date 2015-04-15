package assignment3.chessview.pieces;

import assignment3.chessview.*;

/**
 * The pawn may move forward to the unoccupied square immediately in front of it on the same file, or on its first move it may advance two squares
 * along the same file provided both squares are unoccupied; or the pawn may capture an opponent's piece on a square diagonally in front of it on an
 * adjacent file, by moving to that square. The pawn has two special moves: the en-passant capture and pawn promotion. * @author diego
 */

public class Pawn extends PieceImpl implements Piece {

	/**
	 * {@inheritDocs}
	 */
	private boolean pawnHasTwoSteps = false;

	public Pawn(boolean isWhite) {
		super(isWhite);
	}
	
	public Pawn(boolean isWhite, boolean hasTwoSteps) {
		super(isWhite);
		pawnHasTwoSteps = true;
	}

	public boolean isValidMove(Position oldPosition, Position newPosition, Piece isTaken, Board board) {

		int dir = isWhite ? 1 : -1;
		int oldRow = oldPosition.row();
		int oldCol = oldPosition.column();
		int newRow = newPosition.row();
		int newCol = newPosition.column();

		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);

		if (this.equals(p)) { // make sure this is the right cell
			if (isTaken != null && t != null) { // if the landing position is taken, then "take" in diagonal
				if (isTaken.equals(t)) {
					if (this.isWhite && !t.isWhite() || !this.isWhite && t.isWhite()) { // check that is not taking over the same seed
						if ((oldRow + dir) == newRow && (oldCol + dir) == newCol) {
							return true;
						} else if ((oldRow + dir) == newRow && (oldCol - dir) == newCol) {
							return true;
						}
					}
				}
			} else if (t == null) { // if the landing position is free
				if (board.clearColumnExcept(oldPosition, newPosition, p)) { // check if the road is clear
					if ((oldRow + dir) == newRow && oldCol == newCol) { // if pawn has one step
						return true;
					} else if ((oldRow + dir + dir) == newRow && oldCol == newCol) { // if pawn has 2 steps
						if ((dir == 1 && oldRow == 2) || (dir == -1 && oldRow == 7)) { // make sure this is only admissible for the first move
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isPawnHasTwoSteps() {
		return this.pawnHasTwoSteps;
	}

	public void setPawnHasTwoSteps(boolean twoSteps) {
		this.pawnHasTwoSteps = twoSteps;
	}

	public String toString() {
		if (isWhite) {
			return "P";
		} else {
			return "p";
		}
	}
}
