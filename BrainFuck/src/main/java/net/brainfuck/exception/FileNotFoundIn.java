package net.brainfuck.exception;

/**
 * Created by FranÃ§ois Melkonian on 12/10/2016.

 * Signal when a file to read is not found is detected in the file.
 * This exception will be thrown by <code>LineReader</code>.
 *
 * @author Alexandre Hiltcher
 * @author FranÃ§ois Melkonian
 */
public class FileNotFoundIn extends Exception {


    /**
     *
     */
    private static final long serialVersionUID = -4103585133694610831L;

    /**
     * Constructs a FileNotFoundException with a default message.
     */
    public FileNotFoundIn() {
        super("Input file is finished.");
    }

    /**
     * Constructs a FileNotFoundException with a specified message.
     *
     * @param message the file mane.
     */
    public FileNotFoundIn(String message) {
        super("The input file : -i " + message);
    }

}
