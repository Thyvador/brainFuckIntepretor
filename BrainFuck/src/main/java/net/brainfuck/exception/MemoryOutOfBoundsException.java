package net.brainfuck.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class MemoryOutOfBoundsException extends Exception {

    public MemoryOutOfBoundsException(){
        super("Memory out of bounds.");
    }

    public MemoryOutOfBoundsException(String message){
        super(message);
    }

}
