package net.brainfuck.exception;


/**
 * Signal when an error of syntax is detected in the file.
 *
 * @author FoobarTeam
 */
public class SyntaxErrorException extends Exception {

	private static final long serialVersionUID = 3281162005406034048L;

	/**
     * Constructs a SyntaxErrorException with a default message.
     */
    public SyntaxErrorException() {
        super("Syntax error.");
    }

    /**
     * Constructs a SyntaxErrorException with a specified message.
     *
     * @param message the detail message.
     */
    public SyntaxErrorException(String message) {
        super("Syntax error : " + message);

    }

}
