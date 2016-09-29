package net.brainfuck.exception;


/**
 * Signal when an error of synthaxe is detected in the file.
 * This exception will be thrown by <code>Interpretor</code>.
 *
 * @author Alexandre Hiltcher
 */
public class SynthaxeErrorException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3281162005406034048L;

	/**
     * Constructs a SynthaxeErrorException with a default message.
     */
    public SynthaxeErrorException() {
        super("Synthaxe error.");
    }

    /**
     * Constructs a SynthaxeErrorException with a specified message.
     *
     * @param message the detail message.
     */
    public SynthaxeErrorException(String message) {
        super("Synthaxe error : " + message);
    }

}
