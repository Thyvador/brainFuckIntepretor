package net.brainfuck.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class Exception extends java.lang.Exception{



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
