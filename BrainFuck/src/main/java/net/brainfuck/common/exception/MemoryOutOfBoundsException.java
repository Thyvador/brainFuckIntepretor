package brainfuck.common.exception;

/**
 * Created by Alexandre Hiltcher on 27/09/2016.
 */
public class MemoryOutOfBoundsException extends Exception {

    public MemoryOutOfBoundsException(){
        super();
    }

    public MemoryOutOfBoundsException(String message){
        super(message);
    }

    public MemoryOutOfBoundsException(String message, Throwable cause){
        super(message, cause);
    }

    public MemoryOutOfBoundsException(Throwable cause){
        super(cause);
    }
}
