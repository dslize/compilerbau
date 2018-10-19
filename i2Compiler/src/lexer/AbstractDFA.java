package lexer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import symbols.Letters;
import symbols.Tokens.Token;
import util.Pair;

/**
 * Abstract class for Deterministic Finite Automata.
 */
public abstract class AbstractDFA {

	// Token that is recognized by this automaton
	protected Token token;

	// use data structure for representing
	// - states
	// - final states (and sink states)
	// - transitions of the form (state, input) -> state
	// - current state
	protected Set<Integer> states;
	protected Set<Integer> finalStates;
	protected Set<Integer> sinkStates;
	protected Map<Pair<Integer, Character>, Integer> delta;
	protected Integer currentState;

	/**
	 * Constructor for Abstract DFA.
	 * 
	 * @param token    Token recognized by this automaton.
	 * @param noStates Number of states in the DFA.
	 */
	public AbstractDFA(Token token, int noStates) {
		this.token = token;

		// initialize data structures
		states = new HashSet<>(noStates);

		for (int i = 0; i < noStates; i++) {
			states.add(i);
		}

		finalStates = new HashSet<>(noStates);
		sinkStates = new HashSet<>(noStates);
		delta = new HashMap<>();
		currentState = 0;

		for (char letter : Letters.alpha) {
			delta.put(new Pair<Integer, Character>(0, letter), 0);
		}
		for (char letter : Letters.underScoreNumerical) {
			delta.put(new Pair<Integer, Character>(0, letter), 0);
		}
		for (char letter : Letters.special) {
			delta.put(new Pair<Integer, Character>(0, letter), 0);
		}

	}

	/**
	 * Reset the automaton to the initial state.
	 */
	public void reset() {
		// reset automaton to initial state
		currentState = 0;
	}

	/**
	 * Performs one step of the DFA for a given letter. If there is a transition for
	 * the given letter, then the automaton proceeds to the successor state.
	 * Otherwise it goes to the sink state. By construction it will stay in the sink
	 * for every input letter.
	 * 
	 * @param letter The current input.
	 */
	public void doStep(char letter) {
		// do step by going to the next state according to the current
		// state and the read letter.
		Integer nextState = delta.get(new Pair<>(currentState, letter));

		assert (nextState != null);
		if (nextState != null) {
			currentState = nextState;
		}
	}

	/**
	 * Check if the automaton is currently accepting.
	 * 
	 * @return True, if the automaton is currently in the accepting state.
	 */
	public boolean isAccepting() {
		// return if the current state is accepting
		return finalStates.contains(currentState);
	}

	/**
	 * Run the DFA on the input.
	 * 
	 * @param inputWord String that contains the input word
	 * @return True, if if the word is accepted by this automaton
	 */
	public boolean run(String inputWord) {
		this.reset();
		char[] inputCharWord = inputWord.toCharArray();
		for (char letter : inputCharWord) {
			doStep(letter);
		}
		return isAccepting();
	}

	/**
	 * Checks if the final state can be reached from the current state.
	 * 
	 * @return True, if the state is productive, i.e. the final state can be
	 *         reached.
	 */
	public boolean isProductive() {
		// return if the current state is productive
		return !sinkStates.contains(currentState);
	}

	/**
	 * @return The Token that this automaton recognizes
	 */
	public Token getToken() {
		return token;
	}
}
