package net.brainfuck.exception;

/**
 * Created by Alexandre Hiltcher on 28/09/2016.
 */
public class IllegalStateException extends Exception {

    public IllegalStateException(){
        super("Illegal state exception.");
    }

    public IllegalStateException(String message){
        super(message);
    }

}
