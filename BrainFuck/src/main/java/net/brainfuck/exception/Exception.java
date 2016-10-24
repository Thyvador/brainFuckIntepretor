package net.brainfuck.exception;

/**
 * Signal when a read character is incorrect is detected in the file.
 * This exception will be thrown by <code>LineReader</code>.
 *
 * @author Alexandre Hiltcher
 */
public class Exception extends java.lang.Exception{


    private static final long serialVersionUID = -8989405289648758681L;

    /**
     * Construct a new Exception with a null message.
     */
    public Exception(){
        super("");
    }

    /**
     * Constructs a new Exception with a detailed message.
     * @param message the detailed message.
     */
    public Exception(String message){
        super("");
        System.err.println(message);
    }

    /**
     * Constructs a new Exception from a cause and with a detailed message.
     * 
     * @param message the message of the exception.
     * @param cause the cause of the exception.
     */
    public Exception(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Constructs a new Exception from a cause.
     * @param cause the cause of the exception.
     */
    public Exception(Throwable cause){
        super(cause);
    }
}
