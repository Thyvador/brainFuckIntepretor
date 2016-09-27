package main.java.net.brainfuck.common.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class FileNotFoundException extends Exception{

    public FileNotFoundException(){
        super();
    }

    public FileNotFoundException(String message){
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public FileNotFoundException(Throwable cause){
        super(cause);
    }

}
