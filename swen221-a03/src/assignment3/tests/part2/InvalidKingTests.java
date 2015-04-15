package assignment3.tests.part2;

import org.junit.Test;
import junit.framework.TestCase;
import static assignment3.tests.TestHelpers.*;

public class InvalidKingTests extends TestCase {
	
	public @Test void testInvalidKingMoves() {
		String[] tests = { 
			"Ke1-e3",
			"Ke1-e2",
			"Ke1-d2",
			"e2-e4 e7-e6\nKe1-e3",
			"d2-d4 e7-e6\nKe1-c3",
			"e2-e4 e7-e6\nKe1-e2 e6-e5\nKe1-e2",
			"e2-e4 e7-e5\nKe1-e2 d7-d5\nKe2-d3 c7-c5\nKd3-c4", // This should produce a failure because the King moves in check
			"e2-e4 e7-e5\nKe1-e2 d7-d5\nKe2-d3 c7-c5\nKd3-d4", // This should produce a failure because the King moves in check
		};
		checkInvalidTests(tests);
	}
	
	public @Test void testInvalidKingTakes() {
		String[] tests = {
			"Ke1xe7",
			"Ke1xe2",
			"Ke1xd2",
			"e2-e4 e7-e6\nKe1xe2",
			"e2-e4 b7-b5\nKe1-e2 c7-c6\nKe2xb5"
		};
		
		checkInvalidTests(tests);
	}
}
