package symbols;

/**
 * All recognized letters.
 */
public class Letters {

	// if needed fill in the letters

	// Individual letters
	final static public char[] alpha = "abcdefghijklmnopqrstuvwxyz".concat("abcdefghijklmnopqrstuvwxyz".toUpperCase())
			.toCharArray();

	// Individual digits
	final static public char[] numbers = "1234567890".toCharArray();

	// Underscore and digits
	final static public char[] underScoreNumerical = "_1234567890".toCharArray();

	// Special characters like parenthesis, line breaks, ...
	final static public char[] special = "()/\\,.\n".toCharArray();

}
