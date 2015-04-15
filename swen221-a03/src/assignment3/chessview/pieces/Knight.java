package assignment3.chessview.pieces;

import assignment3.chessview.*;

/**
 * The knight move is unusual among chess pieces. When it moves, it can move to a square that is two squares horizontally and one
 * square vertically, or two squares vertically and one square horizontally. The complete move therefore looks like the letter L.
 * Unlike all other standard chess pieces, the knight can 'jump over' all other pieces (of either color) to its destination
 * square.
 * 
 * @author diego
 *
 */

public class Knight extends PieceImpl implements Piece {
	public Knight(boolean isWhite) {
		super(isWhite);
	}

	public boolean isValidMove(Position oldPosition, Position newPosition, Piece isTaken, Board board) {

		// Check if the Rook is moving horizontal or vertical
		int shiftRows = Math.abs(newPosition.row() - oldPosition.row());
		int shiftCols = Math.abs(newPosition.column() - oldPosition.column());

		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);

		if (this.equals(p)) { // make sure this is the right cell
			if (shiftRows == 2 && shiftCols == 1 || shiftRows == 1 && shiftCols == 2) { // check if the movement is L shaped
				if (isTaken != null && t != null) { // if the landing position is taken, then is a taking move
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
		return false;
	}

	public String toString() {
		if (isWhite) {
			return "N";
		} else {
			return "n";
		}
	}
}
