package net.brainfuck.exception;

import java.lang.*;

/**
 * Created by Alexandre Hiltcher on 28/09/2016.
 */
public class SynthaxErrorException extends Exception {


    public SynthaxErrorException(){
        super();
    }

    public SynthaxErrorException(String message){
        super(message);
    }

    public SynthaxErrorException(String message, Throwable cause){
        super(message, cause);
    }

    public SynthaxErrorException(Throwable cause){
        super(cause);
    }

}
