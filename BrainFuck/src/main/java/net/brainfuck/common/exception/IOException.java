package net.brainfuck.common.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class IOException extends Exception {

    public IOException(){
        super();
    }

    public IOException(String message){
        super(message);
    }

    public IOException(String message, Throwable cause){
        super(message, cause);
    }

    public IOException(Throwable cause){
        super(cause);
    }

}
