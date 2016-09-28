package net.brainfuck.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class IOException extends Exception {

    public IOException(){
        super("IO Exception.");
    }

    public IOException(String message){
        super(message);
    }


}
