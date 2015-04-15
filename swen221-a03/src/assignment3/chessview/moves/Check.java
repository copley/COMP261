package assignment3.chessview.moves;

import assignment3.chessview.*;

/**
 * This represents a "check move". Note that, a check move can only be made up
 * from an underlying simple move; that is, we can't check a check move.
 * 
 * @author djp
 * 
 */
public class Check implements Move {
	private MultiPieceMove move;
	private boolean isWhite;
	
	public Check(MultiPieceMove move) {
		this.move = move;
		this.isWhite = move.isWhite();
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
		
		return move.isValid(board) && nextBoard.isInCheck(!isWhite);
	}
	
	public void apply(Board board) {
		move.apply(board);
	}
	
	public String toString() {
		return move.toString() + "+";
	}
}