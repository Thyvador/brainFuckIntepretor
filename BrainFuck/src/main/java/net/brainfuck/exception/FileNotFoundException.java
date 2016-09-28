package net.brainfuck.exception;

/**
 * Signal when a file to read is not found is detected in the file.
 * This exception will be thrown by <code>Reader</code>.
 *
 * @author Alexandre Hiltcher
 */
public class FileNotFoundException extends Exception {

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
