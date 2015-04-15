package assignment3.chessview.moves;

import assignment3.chessview.*;

public class NonCheck implements Move {
	private MultiPieceMove move;
	
	public NonCheck(MultiPieceMove move) {
		this.move = move;
	}
	
	public MultiPieceMove move() {
		return move;
	}
	
	public boolean isWhite() {
		return move.isWhite();
	}
	
	public boolean isValid(Board board) {
		
		Board nextBoard = new Board(board);
		nextBoard.apply(move);
		
		// This test seemed to fail only if the move was a Pawn Promotion.
		boolean notInCheck = true;
		if(move instanceof PawnPromotion) {
			notInCheck = !nextBoard.isInCheck(!isWhite());
		} else {
			notInCheck = !nextBoard.isInCheck(isWhite());
		}
		
		return move.isValid(board) && notInCheck;
	}
	
	public void apply(Board board) {
		move.apply(board);
	}
	
	public String toString() {
		return move.toString();
	}
}