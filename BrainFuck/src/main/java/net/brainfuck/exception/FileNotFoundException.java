package net.brainfuck.exception;


/**
 * Signal when a file to read is not found.
 *
 * @author Alexandre Hiltcher
 */
public class FileNotFoundException extends IOException {

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
