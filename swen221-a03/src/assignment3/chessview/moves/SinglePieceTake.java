package assignment3.chessview.moves;

import assignment3.chessview.*;
import assignment3.chessview.pieces.King;
import assignment3.chessview.pieces.Piece;

/**
 * A single piece take move is any move which is permitted when taking by either the white or black player. 
 * 
 * @author diego
 *
 */

public class SinglePieceTake extends SinglePieceMove {
	
	private Piece isTaken;
	
	public SinglePieceTake(Piece piece, Piece isTaken, Position oldPosition, Position newPosition) {
		super(piece,oldPosition,newPosition);
		this.isTaken = isTaken;
	}
	
	public boolean isValid(Board board) {
		
		if (board.pieceAt(newPosition) != null) { // Check if the current new position has a piece
			
			if (piece instanceof King) { // Check if is an instance of King
				// Check through opposition pieces to see whether they can take king on nextMove
				King king = (King) piece;
				Position newKingPos = newPosition;
				for (int row = 1; row <= 8; ++row) {
					for (int col = 1; col <= 8; ++col) {
						Position pos = new Position(row, col);
						Piece p = board.pieceAt(pos);
						// If this is an opposition piece, and it can take my king,
						// then we're definitely in check.
						if (p != null && p.isWhite() != king.isWhite() && p.isValidMove(pos, newKingPos, king, board)) {
							// p can take opposition king, so we're in check.						
							return false;
						}
					}
				}
			}
			
			return piece.isValidMove(oldPosition, newPosition, isTaken, board);
		} 
		return false;
	}
	
	public String toString() {
		return pieceChar(piece) + oldPosition + "x" + pieceChar(isTaken) + newPosition; 
	}	
}
