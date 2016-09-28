package net.brainfuck.exception;


/**
 * Created by Alexandre Hiltcher on 28/09/2016.
 */
public class SynthaxErrorException extends Exception {


    public SynthaxErrorException(){
        super("Synthaxe error.");
    }

    public SynthaxErrorException(String message){
        super(message);
    }

}
