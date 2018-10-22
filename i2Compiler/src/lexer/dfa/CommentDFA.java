package lexer.dfa;

import lexer.AbstractDFA;
import symbols.Letters;
import symbols.Tokens.Token;
import util.Pair;

/**
 * DFA recognizing comments.
 */
public class CommentDFA extends AbstractDFA {

	/**
	 * Construct a new DFA that recognizes comments within source code. There are
	 * two kinds of comments: A single line comment starts with // and ends with a
	 * newline and a multiline comment that starts with /* and ends with * /
	 * (without the space)
	 */
	public CommentDFA() {
		// fill in correct number of states - we need 9 productive state and a sink
		// state
		super(Token.COMMENT, 10 /* noStates */);

		// build DFA recognizing comments
		// here we first map all transitions to the sink state
		this.sinkStates.add(9);
		for (int i = 0; i <= 9; i++) {
			for (char letter : Letters.alpha) {
				delta.put(new Pair<Integer, Character>(i, letter), 9);
			}
			for (char letter : Letters.underScoreNumerical) {
				delta.put(new Pair<Integer, Character>(i, letter), 9);
			}
			for (char letter : Letters.special) {
				delta.put(new Pair<Integer, Character>(i, letter), 9);
			}
		}

		delta.put(new Pair<Integer, Character>(0, '/'), 1);

		/* Single-line comments */
		delta.put(new Pair<Integer, Character>(1, '/'), 2);
		// 2 loops on every character that is not '\r' or '\n'
		for (char letter : Letters.alpha) {
			delta.put(new Pair<Integer, Character>(2, letter), 2);
		}
		for (char letter : Letters.underScoreNumerical) {
			delta.put(new Pair<Integer, Character>(2, letter), 2);
		}
		for (char letter : Letters.special) {
			delta.put(new Pair<Integer, Character>(2, letter), 2);
		}
		delta.put(new Pair<Integer, Character>(2, '\n'), 3);
		delta.put(new Pair<Integer, Character>(2, '\r'), 4);
		// for "\r\n":
		delta.put(new Pair<Integer, Character>(4, '\n'), 5);

		this.finalStates.add(3);
		this.finalStates.add(4);
		this.finalStates.add(5);

		/* Multi-line comments */
		delta.put(new Pair<Integer, Character>(1, '*'), 6);
		// 6 loops on every character that is not '*'
		for (char letter : Letters.alpha) {
			delta.put(new Pair<Integer, Character>(6, letter), 6);
		}
		for (char letter : Letters.underScoreNumerical) {
			delta.put(new Pair<Integer, Character>(6, letter), 6);
		}
		for (char letter : Letters.special) {
			delta.put(new Pair<Integer, Character>(6, letter), 6);
		}
		delta.put(new Pair<Integer, Character>(6, '*'), 7);
		// 7 moves back to 6 on every character that is not '/' or '*'
		for (char letter : Letters.alpha) {
			delta.put(new Pair<Integer, Character>(7, letter), 6);
		}
		for (char letter : Letters.underScoreNumerical) {
			delta.put(new Pair<Integer, Character>(7, letter), 6);
		}
		for (char letter : Letters.special) {
			delta.put(new Pair<Integer, Character>(7, letter), 6);
		}
		// on '*' 7 loops
		delta.put(new Pair<Integer, Character>(7, '*'), 7);
		// and on '/' we move to the end of the comment in 8
		delta.put(new Pair<Integer, Character>(7, '/'), 8);

		this.finalStates.add(8);
	}

	/**
	 * Performs one step of the DFA for a given letter. This method works
	 * differently than in the superclass AbstractDFA.
	 * 
	 * @param letter The current input.
	 */
	@Override
	public void doStep(char letter) {
		// We don't see why we would need special treatment here
		super.doStep(letter);
	}
}
