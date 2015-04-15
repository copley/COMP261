package assignment3.chessview.moves;

import assignment3.chessview.*;
import assignment3.chessview.pieces.*;

/**
 * A single piece move is any move which is permitted by either the white or black player. 
 * 
 * @author diego
 *
 */

public class SinglePieceMove implements MultiPieceMove {
	protected Piece piece;
	protected Position oldPosition;
	protected Position newPosition;
	
	public SinglePieceMove(Piece piece, Position oldPosition, Position newPosition) {
		this.piece = piece;
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
	}
	
	public Piece piece() {
		return piece;
	}
	
	public boolean isWhite() {
		return piece.isWhite();
	}
	
	public Position oldPosition() {
		return oldPosition;
	}
	
	public Position newPosition() {
		return newPosition;
	}
		
	public boolean isValid(Board board) {
		
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
		return piece.isValidMove(oldPosition, newPosition, null, board);
	}
	
	public void apply(Board b) {
		// SETTERS FOR CASTLING HAS MOVED 
		if (piece instanceof King) { // if is a king, we set moved true
			if (piece.isWhite()){
				b.setKingWhiteHasMoved(true);				
			} else { // black king
				b.setKingBlackHasMoved(true);
			}
		} 
		if (piece instanceof Rook){ // if is a rook we set the type and moved true
			if (oldPosition.row() == 1) { // is a white piece
				if (oldPosition.column() == 1) { // is a left column
					b.setRookWhiteLeftHasMoved(true);
				} else { // is right column
					b.setRookWhiteRightHasMoved(true);
				}
			} else { // is a black piece
				if (oldPosition.column() == 1) { // is a left column
					b.setRookBlackLeftHasMoved(true);
				} else { // is right column
					b.setRookBlackRightHasMoved(true);
				}
			}
		}
		// SETTERS FOR EN-PASSANT
		if (piece instanceof Pawn) {
			int steps = Math.abs(oldPosition.row()-newPosition.row());
			if (steps > 1) { // has taken more than one steps so is subject to enPassant check
				((Pawn) b.pieceAt(oldPosition)).setPawnHasTwoSteps(true);
			} else {
				((Pawn) b.pieceAt(oldPosition)).setPawnHasTwoSteps(false);
			} 
		}
		// moves the piece
		b.move(oldPosition,newPosition);
	}
	
	public String toString() {
		return pieceChar(piece) + oldPosition + "-" + newPosition; 
	}
	
	protected static String pieceChar(Piece p) {
		if(p instanceof Pawn) {
			return "";
		} else if(p instanceof Knight) {
			return "N";
		} else if(p instanceof Bishop) {
			return "B";
		} else if(p instanceof Rook) {
			return "R";
		} else if(p instanceof Queen) {
			return "Q";
		} else {
			return "K";
		}
	}
}
