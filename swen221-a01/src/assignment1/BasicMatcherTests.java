package assignment1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A very incomplete set of tests for the methods of BasicMatcher. You should improve test coverage by writing 50-100% more tests. You
 * should also write tests for the match method (there are none here). Remember to test edge cases!
 */
public class BasicMatcherTests extends BasicMatcher {

	// =============================================================
	// Regex: "abc"; inputs which do match
	// =============================================================

	@Test
	public void test_1() {
		assertEquals(true, match("abc", "abc"));
	}

	@Test
	public void test_2() {
		assertEquals(true, match("daabc", "abc"));
	}

	@Test
	public void test_3() {
		assertEquals(true, match("daveabcasd", "abc"));
	}

	// =============================================================
	// Regex: "abc"; inputs which don't match
	// =============================================================

	@Test
	public void test_4() {
		assertEquals(false, match("adbc", "abc"));
	}

	@Test
	public void test_5() {
		assertEquals(false, match("da", "abc"));
	}

	@Test
	public void test_6() {
		assertEquals(false, match("ab", "abc"));
	}

	// =============================================================
	// Regex: ".b"; inputs which do match
	// =============================================================

	@Test
	public void test_7() {
		assertEquals(true, match("ab", ".b"));
	}

	@Test
	public void test_8() {
		assertEquals(true, match("bab", ".b"));
	}

	@Test
	public void test_9() {
		assertEquals(true, match("davebee", ".b"));
	}

	// =============================================================
	// Regex: ".b"; inputs which don't match
	// =============================================================

	@Test
	public void test_10() {
		assertEquals(false, match("b", ".b"));
	}

	@Test
	public void test_11() {
		assertEquals(false, match("bd", ".b"));
	}

	@Test
	public void test_12() {
		assertEquals(false, match("dd", ".b"));
	}

	// =============================================================
	// Regex: "bc*"; inputs which do match
	// =============================================================

	@Test
	public void test_13() {
		assertEquals(true, match("daveb", "bc*"));
	}

	@Test
	public void test_14() {
		assertEquals(true, match("davebc", "bc*"));
	}

	@Test
	public void test_15() {
		assertEquals(true, match("dbccaa", "bc*"));
	}

	// =============================================================
	// Regex: "bc*"; inputs which don't match
	// =============================================================

	@Test
	public void test_16() {
		assertEquals(false, match("da", "bc*"));
	}

	@Test
	public void test_17() {
		assertEquals(false, match("cc", "bc*"));
	}

	@Test
	public void test_18() {
		assertEquals(false, match("ccd", "bc*"));
	}

	@Test
	public void test_19() {
		assertEquals(false, match("cd", "bc*"));
	}

	// =============================================================
	// Regex: "^c"; inputs which do match
	// =============================================================

	@Test
	public void test_20() {
		assertEquals(true, match("cdavid", "^c"));
	}

	@Test
	public void test_21() {
		assertEquals(true, match("chello", "^c"));
	}

	@Test
	public void test_22() {
		assertEquals(true, match("c", "^c"));
	}

	// =============================================================
	// Regex: "^c"; inputs which don't match
	// =============================================================

	@Test
	public void test_23() {
		assertEquals(false, match("dcavid", "^c"));
	}

	@Test
	public void test_24() {
		assertEquals(false, match("dc", "^c"));
	}

	@Test
	public void test_25() {
		assertEquals(false, match("", "^c"));
	}

	// =============================================================
	// Regex: "c$"; inputs which do match
	// =============================================================

	@Test
	public void test_26() {
		assertEquals(true, match("davidc", "c$"));
	}

	@Test
	public void test_27() {
		assertEquals(true, match("helklkoc", "c$"));
	}

	@Test
	public void test_28() {
		assertEquals(true, match("c", "c$"));
	}

	// =============================================================
	// Regex: "c$"; inputs which don't match
	// =============================================================

	@Test
	public void test_29() {
		assertEquals(false, match("cd", "c$"));
	}

	@Test
	public void test_30() {
		assertEquals(false, match("d", "c$"));
	}

	@Test
	public void test_31() {
		assertEquals(false, match("", "c$"));
	}

	// =============================================================
	// Regex: ".c*"; inputs which do match
	// =============================================================

	@Test
	public void test_32() {
		assertEquals(true, match("d", ".c*"));
	}

	@Test
	public void test_33() {
		assertEquals(true, match("dc", ".c*"));
	}

	@Test
	public void test_34() {
		assertEquals(true, match("dcc", ".c*"));
	}

	@Test
	public void test_35() {
		assertEquals(true, match("dccd", ".c*"));
	}

	// =============================================================
	// Regex: ".c*"; inputs which don't match
	// =============================================================

	@Test
	public void test_36() {
		assertEquals(false, match("", ".c*"));
	}

	// =============================================================
	// Regex: "^c*$"; inputs which do match
	// =============================================================

	@Test
	public void test_37() {
		assertEquals(true, match("", "^c*$"));
	}

	@Test
	public void test_38() {
		assertEquals(true, match("c", "^c*$"));
	}

	@Test
	public void test_39() {
		assertEquals(true, match("cc", "^c*$"));
	}

	@Test
	public void test_40() {
		assertEquals(true, match("ccc", "^c*$"));
	}

	// =============================================================
	// Regex: "^c*$"; inputs which don't match
	// =============================================================

	@Test
	public void test_41() {
		assertEquals(false, match("dc", "^c*$"));
	}

	@Test
	public void test_42() {
		assertEquals(false, match("cd", "^c*$"));
	}

	@Test
	public void test_43() {
		assertEquals(false, match("cdc", "^c*$"));
	}

	// =============================================================
	// Regex: "^..$"; inputs which do match
	// =============================================================

	@Test
	public void test_44() {
		assertEquals(true, match("aa", "^.."));
	}

	@Test
	public void test_45() {
		assertEquals(true, match("bc", "^.."));
	}

	@Test
	public void test_46() {
		assertEquals(true, match("cdc", "^.."));
	}

	@Test
	public void test_47() {
		assertEquals(true, match("cccd", "^.."));
	}

	// =============================================================
	// Regex: "^c*$"; inputs which don't match
	// =============================================================

	@Test
	public void test_48() {
		assertEquals(false, match("", "^.."));
	}

	@Test
	public void test_49() {
		assertEquals(false, match("d", "^.."));
	}

	@Test
	public void test_50() {
		assertEquals(false, match("a", "^.."));
	}

	// =============================================================
	// Regex: My custom tests, developed while hammering on a keyboard like an ape
	// =============================================================

	@Test
	public void test_51() {
		assertEquals(false, match("", "^."));
	}

	@Test
	public void test_52() {
		assertEquals(false, match("abc", "*"));
	}

	@Test
	public void test_53() {
		assertEquals(true, match("abc", ""));
		//assertEquals(true, matchWithLib("abc", ""));
	}

	@Test
	public void test_54() {
		assertEquals(false, match("", ".$"));
	}

	@Test
	public void test_55() {
		assertEquals(true, match("c", "c*$"));
	}

	@Test
	public void test_56() {
		assertEquals(false, match("ccc", "*$"));
	}

	// =============================================================
	// Regex: Tests for + Operator
	// =============================================================

	@Test
	public void test_57() {
		assertEquals(true, match("davebc", "bc+"));
	}

	@Test
	public void test_58() {
		assertEquals(true, match("dbccaa", "bc+"));
	}

	@Test
	public void test_59() {
		assertEquals(true, match("bc", "bc+"));
	}

	@Test
	public void test_60() {
		assertEquals(false, match("daveb", "bc+"));
	}

	@Test
	public void test_61() {
		assertEquals(false, match("b", "bc+"));
	}

	@Test
	public void test_62() {
		assertEquals(false, match("bac", "bc+"));
	}

	@Test
	public void test_63() {
		assertEquals(false, match("cc", "bc+"));
	}

	@Test
	public void test_64() {
		assertEquals(false, match("ccb", "bc+"));
	}

	@Test
	public void test_65() {
		assertEquals(true, match("davebdc", "b.c+"));
		// assertEquals(true, matchWithLib("davebdc", "b.c+"));
	}

	@Test
	public void test_66() {
		assertEquals(true, match("davebcgbctbc", "bc+"));
	}

	@Test
	public void test_67() {
		assertEquals(true, match("davebcgbctbc", "bc+"));
	}

	@Test
	public void test_68() {
		assertEquals(true, match("davebc", ".bc+"));
	}

	@Test
	public void test_69() {
		assertEquals(false, match("bc", ".bc+"));
		// assertEquals(false, matchWithLib("bc", ".bc+"));
	}

	@Test
	public void test_70() {
		assertEquals(true, match("abbbbc", ".b*c+"));
		// assertEquals(true, matchWithLib("abbbbc", ".b*c+"));
	}

	@Test
	public void test_71() {
		assertEquals(true, match("cfbc", "c.bc+"));
	}

	@Test
	public void test_72() {
		assertEquals(true, match("ac", ".b*c+"));
		// assertEquals(true, matchWithLib("ac", ".b*c+"));
	}

	@Test
	public void test_73() {
		assertEquals(true, match("adc", ".b*c+"));
		//assertEquals(true, matchWithLib("adc", ".b*c+"));
	}

	@Test
	public void test_74() {
		assertEquals(true, match("roraaa", "a$.*"));
	}

	@Test
	public void test_75() {
		assertEquals(false, match("no", "^...*.$"));
		//assertEquals(false, matchWithLib("no", "^...*.$"));

	}

	@Test
	public void test_76() {
		assertEquals(true, match("a", "^.*$"));
	}

	@Test
	public void test_77() {
		assertEquals(true, match("-", "^.*$"));
	}

	@Test
	public void test_78() {
		assertEquals(false, match("", " "));
	}
}
