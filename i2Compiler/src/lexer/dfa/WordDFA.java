package lexer.dfa;

import lexer.AbstractDFA;
import symbols.Letters;
import symbols.Tokens.Token;
import util.Pair;

/**
 * DFA recognizing a given word.
 */
public class WordDFA extends AbstractDFA {

	/**
	 * Construct a new DFA that recognizes exactly the given word. Given a word
	 * "foo" the constructed automaton looks like: -> () -f-> () -o-> () -o-> []
	 * from every state (including the final one) every other input letter leads to
	 * a distinguished sink state in which the automaton then remains
	 * 
	 * @param word  A String that the automaton should recognize
	 * @param token The token corresponding to the recognized word.
	 */
	public WordDFA(String word, Token token) {
		// fill in correct number of states
		// initial state, one per letter, and sink state
		super(token, word.length() + 2 /* noStates */);

		assert (word.length() > 0);

		// build DFA recognizing the given word
		for (int i = 0; i < word.length() + 2; i++) {
			for (char letter : Letters.alpha) {
				delta.put(new Pair<Integer, Character>(0, letter), word.length() + 1);
			}
			for (char letter : Letters.underScoreNumerical) {
				delta.put(new Pair<Integer, Character>(0, letter), word.length() + 1);
			}
			for (char letter : Letters.special) {
				delta.put(new Pair<Integer, Character>(0, letter), word.length() + 1);
			}
		}

		for (int i = 0; i < word.length(); i++) {
			this.delta.put(new Pair<Integer, Character>(i, word.charAt(i)), i + 1);
		}

		this.finalStates.add(word.length());
		this.sinkStates.add(word.length() + 1);
	}
}
