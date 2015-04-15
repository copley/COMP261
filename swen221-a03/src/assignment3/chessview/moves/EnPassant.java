package assignment3.chessview.moves;

import assignment3.chessview.*;
import assignment3.chessview.pieces.*;

/**
 * This represents an "en passant move" --- http://en.wikipedia.org/wiki/En_passant.
 * 
 * @author djp
 * 
 */
public class EnPassant implements MultiPieceMove {
	private SinglePieceMove move;
	private boolean isWhite;

	public EnPassant(SinglePieceMove move) {
		this.move = move;
		this.isWhite = move.isWhite();
	}

	public boolean isWhite() {
		return isWhite;
	}

	public boolean isValid(Board board) {
		Position passantPosition = getPassantPosition(); // pawn that is passing by
		if (!(board.pieceAt(passantPosition) instanceof Pawn)) { // if is not a pawn enPassant doesn't apply
			return false;
		} else if (((Pawn) board.pieceAt(passantPosition)).isPawnHasTwoSteps() == false) { // if is a pawn then check if has taken two steps
			return false;
		}
		return true;
	}

	public void apply(Board board) {
		Position passantPosition = getPassantPosition();
		board.setPieceAt(passantPosition, null);
		move.apply(board);
	}

	public Position getPassantPosition() {
		Position newPosition = move.newPosition(); // next position
		Position passantPosition = null;
		if (isWhite) {
			passantPosition = new Position(newPosition.row() - 1, newPosition.column());
		} else {
			passantPosition = new Position(newPosition.row() + 1, newPosition.column());
		}
		return passantPosition;
	}

	public String toString() {
		return move.toString() + "ep";
	}
}