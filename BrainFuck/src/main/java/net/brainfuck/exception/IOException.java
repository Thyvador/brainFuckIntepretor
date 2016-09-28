package net.brainfuck.exception;

/**
 * Signal when an error input or output is detected in the file.
 * This exception will be thrown by <code>Reader</code>, <code>FileReader</code>.
 *
 * @author Alexandre Hiltcher
 */
public class IOException extends Exception {

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
        super("IOException :" + message);
    }


}
