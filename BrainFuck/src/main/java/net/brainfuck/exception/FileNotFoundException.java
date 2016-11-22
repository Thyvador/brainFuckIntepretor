package net.brainfuck.exception;

// TODO: Auto-generated Javadoc

/**
 * Signal when a file to read is not found.
 * This exception will be thrown by <code>BfReader</code>.
 *
 * @author Alexandre Hiltcher
 */
public class FileNotFoundException extends Exception {

	private static final long serialVersionUID = -4105585133694610831L;

	/**
	 * Instantiates a new brackets parse exception with a specific message.
	 */
	public FileNotFoundException() {
		super("File not found.");
	}

	/**
	 * Instantiates a new brackets parse exception with a specific message.
	 *
	 * @param message the message
	 */
	public FileNotFoundException(String message) {
		super("File not found : " + message);
	}

}
