package net.brainfuck.exception;

/**
 * Signal when an memory overflow is detected in the file.
 * This exception will be thrown by <code>Memory</code>.
 *
 * @author Alexandre Hiltcher
 */
public class MemoryOverFlowException extends Exception {

    /**
     * Constructs a MemoryOverFlowException with a default message.
     */
    public MemoryOverFlowException() {
        super("Illegal state exception.");
    }

    /**
     * Constructs a MemoryOverFlowException with a specified message.
     *
     * @param message the detail message.
     */
    public MemoryOverFlowException(String message) {
        super("Memory over flow exception : " + message);
    }

}
