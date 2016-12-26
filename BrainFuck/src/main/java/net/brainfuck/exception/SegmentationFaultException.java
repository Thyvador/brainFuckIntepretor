package net.brainfuck.exception;

public class SegmentationFaultException extends Exception {

	private static final long serialVersionUID = 3989494324978994414L;

    public SegmentationFaultException() {
        super("Core dumped.");
    }

    public SegmentationFaultException(String message) {
        super("Core dumped : " + message);
    }
	
}
