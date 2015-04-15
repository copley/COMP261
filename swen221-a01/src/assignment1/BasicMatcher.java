package assignment1;

import java.util.regex.Pattern;

/**
 * A basic regular expression matcher. Matches regular expressions consisting of .*$^ and any character literal.
 * <p>
 * A regular expressions (or regexes) are a very small language for describing text strings. A regex describes zero or
 * more strings; a regex matcher attempts to match a text string with a regex. A match occurs if the text string is
 * described by the regex. See <a href="http://en.wikipedia.org/wiki/Regex"> wikipedia </a> for more details
 * <p>
 * A regular expression consists of characters which match against themselves and control characters which have special
 * behaviour, such as wildcard characters. There are many varieties of regular expression languages with different
 * syntax and semantics. The language for this assignment is a small subset of the most common language, used in Perl
 * and Java and elsewhere.
 * <p>
 * If you are unclear on whether a regex should match a text string or not you could use the Java regex library to
 * compare against, this might be useful in the edge cases. The method matchWithLib will help you do this. Note that you
 * may not use the Java regex library in your solutions (only to help you understand regular expressions).
 *
 * <ul>
 * <li>A character literal matches that character.
 * <li>. matches any character
 * <li>* matches zero or more occurrences of the preceding character (if there is no preceding char, then never matches)
 * <li>^ matches the start of a string
 * <li>$ matches the end of a string
 * </ul>
 */

public class BasicMatcher implements Matcher {

	/**
	 * {@inheritDoc}
	 */

	public boolean match(String text, String regex) {
		// SPECIAL CASE: regex starts with '^'
		if (regex.length() != 0 && regex.charAt(0) == '^') {
			return matchHere(text, regex.substring(1));
		}
		// SPECIAL CASE: text is empty and regex donesn't contain '*'
		if (text.length() == 0 && !regex.contains("*")) {
			return false;
		}
		// Loop through the text attempting to match, quit at the first success
		// or the end of the text
		do {
			if (matchHere(text, regex)) {
				return true;
			}
			if (text.length() == 0) {
				return false;
			}
			text = text.substring(1);
			// NOTE: removed the matchCahr call form while loop to avoid fails while checking the text
			// } while (text.length() != 0 && matchChar(text,regex));
		} while (text.length() != 0);
		return false;
	}

	/**
	 * Returns whether the given regular expression can be matched against the start of the text string. This is in
	 * contrast to the {@link #match(String, String)} method which attempts to match the regular expression anywhere in
	 * the text string.
	 *
	 * @param text
	 *            some text to match
	 * @param regex
	 *            a regular expression to match against
	 * @return true if the regular expression can be matched against the start of the string; false otherwise
	 */
	protected boolean matchHere(String text, String regex) {

		// SPECIAL CASE: Check if regex is empty
		if (regex.length() == 0)
			return true;

		// SPECIAL CASE: Check for '*'
		if (regex.length() > 1 && regex.charAt(1) == '*') {
			return matchStar(text, regex);
		}

		// SPECIAL CASE: regex "ends with"
		if (regex.length() > 1 && regex.charAt(1) == '$') {
			if (text.length() > 0) {
				return matchChar(text.substring(text.length() - 1), regex);
			}
			return false;
		}
		// check if the last character is '$' in then return true if text is empty (success)
		if (regex.length() == 1 && regex.charAt(0) == '$') {
			return text.length() == 0;
		}

		// SPECIAL CASE: regex contains "add"
		if (regex.length() > 1 && regex.charAt(1) == '+') {
			return matchAdd(text, regex);
		}

		// recursively match the whole text with the regex, one char at a time
		if (matchChar(text, regex)) {
			if (text.length() > 0 && regex.length() > 0) {
				return matchHere(text.substring(1), regex.substring(1));
			}
		}
		return false;
	}

	/**
	 * Assumes that the start of the regex is some character followed by a '*' character. It tries to match the first
	 * character of the regular expression zero or more times against the text input, and then attempts to match the
	 * rest of the regular expression against the rest of the text input.
	 *
	 * @param text
	 *            some text to match
	 * @param regex
	 *            the regular expression; must be at least two characters long, and the second should be '*'
	 * @return true if the match is successful, false otherwise.
	 */
	protected boolean matchStar(String text, String regex) {
		String restOfRegex = regex.substring(2);
		if (matchHere(text, restOfRegex)) {
			return true;
		}
		if (text.length() == 0) {
			return false;
		}
		// match as few characters as possible and try to match the rest of the regex
		// this effectively backtracks when we can't match the rest of the regex and tries matching more characters to the star
		while (matchChar(text, regex)) {
			text = text.substring(1);
			if (matchHere(text, restOfRegex)) {
				return true;
			}
			if (text.length() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Matches the first character of the text against the first character of the regex.
	 * <p>
	 * 
	 * @param text
	 *            to be matched against the regular expression
	 * @param regex
	 *            the regular expression used for matching
	 * @return true if the first characters match; false otherwise.
	 */
	protected boolean matchChar(String text, String regex) {
		if (text.length() > 0 && regex.length() > 0) {
			return regex.charAt(0) == '.' || (regex.charAt(0) == text.charAt(0) && text.charAt(0) != '*');
		}
		return false;
	}

	protected boolean matchAdd(String text, String regex) {
		return matchChar(text, regex);
	}

	/**
	 * Attempts to match text against regex using the Java regex library.
	 * <p>
	 * This method is not used by the above matching code, and you must NOT use it in your solution! However, you may
	 * find it helpful for testing.
	 *
	 * @param text
	 *            - A text string to match (String)
	 * @param regex
	 *            - A regular expression to match against (String)
	 * @return true if the match is successful, false otherwise
	 */
	public boolean matchWithLib(String text, String regex) {
		Pattern p = Pattern.compile(regex);
		java.util.regex.Matcher m = p.matcher(text);
		return m.find();
	}
}
