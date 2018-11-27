package parser;

import java.util.List;

/**
 * Exception thrown by the parser.
 */
public class ParserException extends Exception {

	private static final long serialVersionUID = 1L;

	// Recognized rules up to the exception
	private List<Rule> analysisBeforeFailure = null;

	/**
	 * Constructor.
	 */
	public ParserException() {
	}

	/**
	 * Constructor with exception text.
	 * 
	 * @param s
	 *            Text explaining the exception.
	 */
	public ParserException(String s) {
		super(s);
	}

	/**
	 * Constructor.
	 * 
	 * @param s
	 *            Text explaining the exception.
	 * @param analysis
	 *            Recognized rules up to the exception.
	 */
	public ParserException(String s, List<Rule> analysis) {
		this(s);
		this.analysisBeforeFailure = analysis;
	}

	/**
	 * Gets the recognized rules up to the exception.
	 * 
	 * @return List of rules recognized before the failure.
	 */
	public List<Rule> getAnalysisBeforeFailure() {
		return analysisBeforeFailure;
	}
}
