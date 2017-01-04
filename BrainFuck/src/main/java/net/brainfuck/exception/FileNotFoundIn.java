package net.brainfuck.exception;


/**
 * Signal when a file to read is not found is detected in the file.
 *
 * @author Alexandre Hiltcher
 * @author Francois Melkonian
 */
public class FileNotFoundIn extends Exception {

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
     * @param filename the file mane.
     */
    public FileNotFoundIn(String filename) {
        super("The input file : -i " + filename);
    }

}
