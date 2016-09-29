package net.brainfuck.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class Exception extends java.lang.Exception{



    /**
	 * 
	 */
	private static final long serialVersionUID = -8989405289648758681L;

	public Exception(){
        super("");
        System.exit(2);
    }

    public Exception(String message){
        super("");
        System.err.println(message);
        System.exit(2);
    }

    public Exception(String message, Throwable cause){
        super(message, cause);
    }

    public Exception(Throwable cause){
        super(cause);
    }
}
