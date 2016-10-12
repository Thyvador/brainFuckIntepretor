package net.brainfuck.exception;

/**
 * Created by Alexandre Hiltcher on 12/10/2016.
 */
public class IncorrectArgumentException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -5511374365344567790L;

    /**
     * Constructs a MemoryOutOfBounds with a default message.
     */
    public IncorrectArgumentException() {
        super("Incorrect Argument found.");
    }

    /**
     * Constructs a MemoryOutOfBounds with a specified message.
     *
     * @param message the specified message.
     */
    public IncorrectArgumentException(String message) {
        super("Incorrect argument : " + message);
    }


}
