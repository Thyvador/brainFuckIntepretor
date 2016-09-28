package net.brainfuck.exception;

/**
 * Signal when a read character is incorrect is detected in the file.
 * This exception will be thrown by <code>Reader</code>.
 *
 * @author Alexandre Hiltcher
 */
public class CharacterException extends Exception {

    private static final long serialVersionUID = 7123817596132293186L;

    /**
     * Constructs a CharacterException with a default message.
     */
    public CharacterException() {
        super("Character exception.");
    }

    /**
     * Constructs a CharacterException with a specified message.
     *
     * @param message the detail message.
     */
    public CharacterException(String message) {
        super("Character '" + message + "' is incorect.");
    }

}
