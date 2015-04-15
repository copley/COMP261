package assignment3.chessview.moves;

import assignment3.chessview.*;
import assignment3.chessview.pieces.*;

/**
 * Promotion is a chess rule that a pawn that reaches its eighth rank is immediately changed into the player's choice of a queen, knight, rook, or
 * bishop of the same color. The new piece replaces the pawn on the same square, as part of the same move. The choice of new piece is not limited
 * to pieces that have already been captured. Every pawn that reaches its eighth rank must be promoted. Pawn promotion, or the threat of it, often
 * decides the result of a chess end-game.
 * 
 * @author diego
 *
 */
public class PawnPromotion implements MultiPieceMove {
	
	private Piece promotion;
	private SinglePieceMove move;

	public PawnPromotion(SinglePieceMove move, Piece promotion) {
		this.move = move;
		this.promotion = promotion;
	}

	public boolean isWhite() {
		return promotion.isWhite();
	}

	public boolean isValid(Board board) {
		Position oldPosition = move.oldPosition();
		Position newPosition = move.newPosition();
		if (board.pieceAt(oldPosition) instanceof Pawn) { // if is not a pawn enPassant doesn't apply
			if (this.isWhite() && newPosition.row() == 8 ) { // if white pawn and reaches the other end of board
				return true;
			} else if (!this.isWhite() && newPosition.row() == 1 ) { // black pawn
				return true;
			}
		}
		return false;
	}

	public void apply(Board board) {
		board.apply(move);
		board.setPieceAt(move.newPosition(), promotion);
	}

	public String toString() {
		return super.toString() + "=" + SinglePieceMove.pieceChar(promotion);
	}
}
