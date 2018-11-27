package parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import parser.grammar.AbstractGrammar;
import symbols.Alphabet;
import symbols.NonTerminal;
import util.Pair;

/**
 * Generator all LR(0) sets for a given grammar.
 */
public class LR0SetGenerator {

	// Grammar.
	private AbstractGrammar grammar;

	// Complete state space.
	private HashSet<LR0Set> states;

	// Initial state.
	private LR0Set initialState;

	// Transitions.
	private HashMap<Pair<LR0Set, Alphabet>, LR0Set> transitions;

	/**
	 * Constructor.
	 * 
	 * @param grammar
	 *            Grammar.
	 */
	public LR0SetGenerator(AbstractGrammar grammar) {
		this.grammar = grammar;
		this.states = new HashSet<LR0Set>();
		this.transitions = new HashMap<Pair<LR0Set, Alphabet>, LR0Set>();
		generateLR0StateSpace();
	}

	/**
	 * Add new LR(0) set
	 * 
	 * @param state
	 *            LR(0) set
	 */
	private void addState(LR0Set state) {
		assert (!states.contains(state));
		states.add(state);
	}

	/**
	 * Add new transition.
	 * 
	 * @param source
	 *            Source LR(0) set
	 * @param letter
	 *            Letter
	 * @param target
	 *            Target LR(0) set
	 */
	private void addTransition(LR0Set source, Alphabet letter, LR0Set target) {
		assert (states.contains(source));
		assert (states.contains(target));
		assert (!transitions.containsKey(new Pair<LR0Set, Alphabet>(source, letter)));
		transitions.put(new Pair<LR0Set, Alphabet>(source, letter), target);
	}

	/**
	 * Get successor LR(0) set.
	 * 
	 * @param source
	 *            Source LR(0) set.
	 * @param letter
	 *            Letter.
	 * @return Returns the letter-successor of source, or null if no such
	 *         mapping exists.
	 */
	public LR0Set getSuccessor(LR0Set source, Alphabet letter) {
		return transitions.get(new Pair<LR0Set, Alphabet>(source, letter));
	}

	/**
	 * Generate all LR(0) sets and the corresponding automaton for the given
	 * grammar.
	 */
	private void generateLR0StateSpace() {
		// TODO implement state space generation

	}

	/**
	 * Compute the epsilon closure for the given LR(0) set.
	 * 
	 * @param set
	 *            LR(0) set
	 * @return LR(0) set representing the epsilon closure.
	 */
	private LR0Set epsilonClosure(LR0Set set) {
		// TODO it might be helpful to implement this method.
		return null;
	}

	/**
	 * From a set of rules of the form A -> ab | aC create the "fresh" items A
	 * -> * ab, A -> * aC
	 * 
	 * @param lhs
	 *            Non-terminal on left-hand side
	 * @return A set of items with nothing left of the marker
	 */
	private LR0Set freshItems(NonTerminal lhs) {
		LR0Set result = new LR0Set();
		List<Rule> rules = grammar.getRules(lhs);
		for (Rule rule : rules) {
			result.add(LR0Item.freshItem(rule));
		}
		return result;
	}

	/**
	 * Get number of conflicts.
	 * 
	 * @return Number of conflicts.
	 */
	public int nrConflicts() {
		int counter = 0;
		for (LR0Set lr0set : states) {
			if (lr0set.hasConflicts())
				counter++;
		}
		return counter;
	}

	/**
	 * Get number of states.
	 * 
	 * @return Number of states.
	 */
	public int nrStates() {
		return states.size();
	}

	/**
	 * Get initial LR(0) set.
	 * 
	 * @return Initial LR(0) set
	 */
	public LR0Set getInitialState() {
		return initialState;
	}

	/**
	 * Print all LR(0) sets.
	 */
	public void printLR0Sets() {
		for (LR0Set set : states) {
			System.out.println(set.getSequenceAsString() + ": " + set);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("digraph{");
		for (LR0Set state : states) {
			builder.append(state.getSequenceAsString());
			builder.append(" [label=\"");
			builder.append(state.toString());
			builder.append("\"];\n");
		}
		builder.append("initial state: ");
		builder.append(initialState.getSequenceAsString());
		builder.append("\n");

		Iterator<Entry<Pair<LR0Set, Alphabet>, LR0Set>> it = transitions.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Pair<LR0Set, Alphabet>, LR0Set> entry = it.next();
			Pair<LR0Set, Alphabet> pair = entry.getKey();
			builder.append(pair.getFirst().getSequenceAsString());
			builder.append(" -> ");
			builder.append(entry.getValue().getSequenceAsString());
			builder.append(" [label=\"");
			builder.append(pair.getSecond());
			builder.append("\"];\n");
		}
		builder.append("}");
		return builder.toString();
	}
}
