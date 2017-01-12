package net.brainfuck.exception;

/**
 * @author FoobarTeam
 */
public class SegmentationFaultException extends Exception {

	private static final long serialVersionUID = 3989494324978994414L;

    /**
     * Exception when the core is dumped
     */
    public SegmentationFaultException() {
        super("Core dumped.");
    }

    public SegmentationFaultException(String message) {
        super("Core dumped : " + message);
    }
	
}
