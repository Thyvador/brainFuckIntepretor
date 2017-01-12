package net.brainfuck.exception;

/**
 * Signal when an error input or output is detected in the file.
 *
 * @author FoobarTeam
 */
public class IOException extends Exception {

	private static final long serialVersionUID = 5444607441404760156L;

	/**
     * Constructs a IOException with a default message.
     */
    public IOException() {
        super("IO Exception.");
    }

    /**
     * Constructs a IOException with a specified message.
     *
     * @param message the detail message.
     */
    public IOException(String message) {
        super("IOException : " + message);
    }


}
