package net.brainfuck.exception;

// TODO: Auto-generated Javadoc
/**
 * Signal when a file to read is not found is detected in the file.
 * This exception will be thrown by <code>BfReader</code>.
 *
 * @author Alexandre Hiltcher
 */
public class FileNotFoundException extends Exception {

	private static final long serialVersionUID = -4105585133694610831L;

	/**
     * Constructs a FileNotFoundException with a default message.
     */
    public FileNotFoundException() {
        super("File not found.");
    }

    /**
     * Constructs a FileNotFoundException with a specified message.
     *
     * @param message the file mane.
     */
    public FileNotFoundException(String message) {
        super("File not found : " + message);
    }

}
