package net.brainfuck.exception;

/**
 * Signal when a bound excess error is detected in the file.
 * This exception will be thrown by <code>Memory</code>.
 *
 * @author Alexandre Hiltcher
 */
public class MemoryOutOfBoundsException extends Exception {


    /**
	 * 
	 */
	private static final long serialVersionUID = -1814144013450333089L;

	/**
     * Constructs a MemoryOutOfBounds with a default message.
     */
    public MemoryOutOfBoundsException() {
        super("Memory out of bounds.");
    }

    /**
     * Constructs a MemoryOutOfBounds with a specified message.
     *
     * @param message the specified message.
     */
    public MemoryOutOfBoundsException(String message) {
        super("Memory out of bounds : " + message);
    }

}
