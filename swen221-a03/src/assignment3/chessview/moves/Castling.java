package assignment3.chessview.moves;

import assignment3.chessview.*;
import assignment3.chessview.pieces.*;

/**
 * Castling is a move in the game of chess involving a player's king and either of the player's original rooks. It is the only move in chess in which
 * a player moves two pieces in the same move, and it is the only move aside from the knight's move where a piece can be said to "jump over" another.
 * 
 * Castling is permissible if and only if all of the following conditions hold (Schiller 2001:19): The king and the chosen rook are on the player's
 * first rank. Neither the king nor the chosen rook have previously moved. There are no pieces between the king and the chosen rook. The king is not
 * currently in check. The king does not pass through a square that is attacked by an enemy piece. The king does not end up in check. (True of any
 * legal move.)
 */

public class Castling implements MultiPieceMove {

	/**
	 * {@inheritDocs}
	 */

	private boolean kingSide;
	private boolean isWhite;
	private Position kingPos;
	private Position kingNewPos;
	private Position rookPos;
	private Position rookNewPos;
	private King kingPiece;
	private Rook rookPiece;
	private boolean kingIsInCheck;

	public Castling(boolean isWhite, boolean kingSide) {
		
		this.isWhite = isWhite;
		this.kingSide = kingSide;
		this.kingIsInCheck = false;

		// if is king side move the king two cells right
		// then move the rook to the left of the king (col-1)
		// if is !king side moves the king two cells left
		// then move the rook to the right (col+1)
		if (isWhite) {
			kingPos = new Position(1, 5);
			if (kingSide) {
				rookPos = new Position(1, 8);
				kingNewPos = new Position(1, 7);
				rookNewPos = new Position(1, 6);
			} else {
				rookPos = new Position(1, 1);
				kingNewPos = new Position(1, 3);
				rookNewPos = new Position(1, 4);
			}
		} else { // is black
			kingPos = new Position(8, 5);
			if (kingSide) {
				rookPos = new Position(8, 8);
				kingNewPos = new Position(8, 7);
				rookNewPos = new Position(8, 6);
			} else {
				rookPos = new Position(8, 1);
				kingNewPos = new Position(8, 3);
				rookNewPos = new Position(8, 4);
			}
		}
	}

	public void apply(Board board) {

		// apply the movement
		board.move(kingPos, kingNewPos);
		board.move(rookPos, rookNewPos);
	}

	public boolean isValid(Board board) {
//		System.out.println("Checking if castling is valid");
						
		// check if is king and rook's first move
		boolean hasKingWhiteMoved = board.getKingWhiteHasMoved();
		boolean hasKingBlackMoved = board.getKingBlackHasMoved();
		boolean hasWhiteLeftRookMoved = board.getRookWhiteLeftHasMoved();
		boolean hasWhiteRightRookMoved = board.getRookWhiteRightHasMoved();
		boolean hasBlackLeftRookMoved = board.getRookBlackLeftHasMoved();
		boolean hasBlackRightRookMoved = board.getRookBlackRightHasMoved();
		
		// check if king and rook are at the right position and sets the pieces
		if (board.pieceAt(kingPos) instanceof King) {
			this.kingPiece = (King) board.pieceAt(kingPos);	
		}
		if (board.pieceAt(rookPos) instanceof Rook) {
			this.rookPiece = (Rook) board.pieceAt(rookPos);
		}
		
		// check if the king is under check before moving
		if ((isWhite && board.isInCheck(isWhite)) || (!isWhite && board.isInCheck(!isWhite))) {
			kingIsInCheck = true;
		}
				
		// check if the path is clear
		boolean clear = board.clearRowExcept(kingPos, rookPos, this.kingPiece, this.rookPiece);	
		
		// WHITE LEFT SIDE (queen side)
		if (isWhite) {
			if (!kingSide) {
				for (int i=kingPos.column(); i >= kingNewPos.column(); i--){ // check for every cell on the path that is not setting white king on check
					boolean pathIsOnCheck = this.kingPiece.isOnCheck(new Position(1, i), board);
					if (pathIsOnCheck) {
						return false;
					}
				}
				return (clear && !hasKingWhiteMoved && !hasWhiteLeftRookMoved && !kingIsInCheck);
				}
			}		
		
		// WHITE RIGHT SIDE (king side)
		if (isWhite) {
			if (kingSide) {
				for (int i=kingPos.column(); i <= kingNewPos.column(); i++){ // check for every cell on the path that is not setting white king on check
					boolean pathIsOnCheck = this.kingPiece.isOnCheck(new Position(1, i), board);
					if (pathIsOnCheck) {
						return false;
					}
				}
				return (clear && !hasKingWhiteMoved && !hasWhiteRightRookMoved && !kingIsInCheck);
				}
			}
		
		// BLACK LEFT SIDE (queen side)
		if (!isWhite) {
			if (!kingSide) {
				for (int i=kingPos.column(); i >= kingNewPos.column(); i--){ // check for every cell on the path that is not setting white king on check
					boolean pathIsOnCheck = this.kingPiece.isOnCheck(new Position(8, i), board);
					if (pathIsOnCheck) {
						return false;
					}
				}
				return (clear && !hasKingBlackMoved && !hasBlackLeftRookMoved && !kingIsInCheck);
				}
			}		
		
		// BLACK RIGHT SIDE (king side)
		if (!isWhite) {
			if (kingSide) {
				for (int i=kingPos.column(); i <= kingNewPos.column(); i++){ // check for every cell on the path that is not setting white king on check
					boolean pathIsOnCheck = this.kingPiece.isOnCheck(new Position(8, i), board);
					if (pathIsOnCheck) {
						return false;
					}
				}
				return (clear && !hasKingBlackMoved && !hasBlackRightRookMoved && !kingIsInCheck);
				}
			}
		
		return false;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public boolean isKingSide() {
		return kingSide;
	}

	public String toString() {
		if (kingSide) {
			return "O-O";
		} else {
			return "O-O-O";
		}
	}
}
