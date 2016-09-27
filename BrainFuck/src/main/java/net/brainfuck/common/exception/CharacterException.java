package main.java.net.brainfuck.common.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class CharacterException extends Exception {

    public CharacterException(){
        super();
    }

    public CharacterException(String message){
        super(message);
    }

    public CharacterException(String message, Throwable cause){
        super(message, cause);
    }

    public CharacterException(Throwable cause){
        super(cause);
    }
}
