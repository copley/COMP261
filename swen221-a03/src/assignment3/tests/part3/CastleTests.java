package assignment3.tests.part3;

import org.junit.Test;
import junit.framework.TestCase;
import static assignment3.tests.TestHelpers.*;

public class CastleTests extends TestCase {
	
	public @Test void testValidCastling() {
		String[][] tests = { 
				// Test 1 Check castling on the white, king side
				{"e2-e4 e7-e6\nNg1-f3 d7-d6\nBf1-c4 f7-f6\nO-O",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|_|_|_|p|p|\n"+
				 "6|_|_|_|p|p|p|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|B|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|N|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|_|R|K|_|\n"+
				 "  a b c d e f g h"
				},
				// Test 2 Check castling on black, king side
				{"e2-e3 e7-e6\nd2-d3 Ng8-f6\nc2-c3 Bf8-c5\na2-a3 O-O",
				 "8|r|n|b|q|_|r|k|_|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|p|n|_|_|\n"+
				 "5|_|_|b|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|P|_|P|P|P|_|_|_|\n"+
				 "2|_|P|_|_|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3 Check castling on white, long side
				{"d2-d4 a7-a6\nQd1-d3 b7-b6\nBc1-e3 c7-c6\nNb1-a3 d7-d6\nO-O-O",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|_|_|_|_|p|p|p|p|\n"+
				 "6|p|p|p|p|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|P|_|_|_|_|\n"+
				 "3|N|_|_|Q|B|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|_|_|K|R|_|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4 check castling on black, long side
				{"a2-a3 d7-d5\nb2-b3 Qd8-d6\nc2-c3 Bc8-e6\nd2-d3 Nb8-a6\ne2-e3 O-O-O",
				 "8|_|_|k|r|_|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|n|_|_|q|b|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|P|P|P|P|P|_|_|_|\n"+
				 "2|_|_|_|_|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				}
		
		};
		checkValidTests(tests);
	}
	
	public @Test void testInvalidCastling() {
		String[] tests = {
			"e2-e4 e7-e6\nNg1-f3 d7-d6\nO-O", // not valid because there isn't a clean path between king and rook 
			"e2-e3 e7-e6\nd2-d3 Ng8-f6\nc2-c3 O-O", // not valid because there isn't a clean path between king and rook
			"d2-d4 a7-a6\nQd1-d3 b7-b6\nNb1-a3 d7-d6\nO-O-O", // not valid because there isn't a clean path between king and rook
			"a2-a3 d7-d5\nb2-b3 Qd8-d6\nc2-c3 Bc8-e6\ne2-e3 O-O-O", // not valid because there isn't a clean path between king and rook
			"e2-e4 e7-e6\nNg1-f3 d7-d6\nBf1-c4 f7-f6\nKe1-f1 g7-g6\nKf1-e1 h7-h6\nO-O", // not valid because is not king's first move
			"d2-d4 a7-a6\nQd1-d3 b7-b6\nBc1-e3 c7-c6\nNb1-a3 d7-d6\nKe1-d1 g7-g6\nKd1-e1 h7-h6\nO-O-O", // not valid because is not king's first move
			"d2-d3 c7-c6\nNb1-a3 Qd8-b6\nBc1-h6 e7-e6\nc2-c4 a7-a6\nQd1-a4 Qb6-a5\nO-O-O", // not valid because white king is under check
			"a2-a3 f7-f5\nc2-c3 Ng8-f6\nQd1-a4 g7-g6\nQa4-b4 Bf8-h6\nb2-b3 e7-e6\ne2-e3 O-O" // not valid because black king is under check

		};
		
		checkInvalidTests(tests);
	}
}
