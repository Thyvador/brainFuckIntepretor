package net.brainfuck.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class BracketsParseException.
 */
public class BracketsParseException extends Exception {

	private static final long serialVersionUID = -4475769070470707772L;

	/**
	 * Instantiates a new brackets parse exception.
	 */
	public BracketsParseException() {
		super("Bad parenthesized expression.");
	}
	
	/**
	 * Instantiates a new brackets parse exception.
	 *
	 * @param string
	 *            the string
	 */
	public BracketsParseException(String string) {
		super("Bad parenthesized expression: missing " + string + ".");
	}

}
