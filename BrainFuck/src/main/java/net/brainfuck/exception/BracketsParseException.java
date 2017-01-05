package net.brainfuck.exception;

/**
 * Signal when bracket has no corresponding bracket.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 */
public class BracketsParseException extends Exception {

    private static final long serialVersionUID = -4475769070470707772L;

    /**
     * Instantiates a new brackets parse exception with a default message.
     */
    public BracketsParseException() {
        super("Bad parenthesized expression.");
    }

    /**
     * Instantiates a new brackets parse exception with a specific message.
     *
     * @param message the message
     */
    public BracketsParseException(String message) {
        super("Bad parenthesized expression: missing " + message + ".");
    }

}
