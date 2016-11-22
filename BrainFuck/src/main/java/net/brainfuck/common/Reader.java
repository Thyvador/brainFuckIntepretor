package net.brainfuck.common;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;

// TODO: Auto-generated Javadoc
/**
 * Created by François MELKONIAN on 28/09/2016.
 * Reader interface used to read bf instruction in "short" , "long" and picture format.
 */
public interface Reader {

    /**
	 * Read the file to see if there is an other instruction.
	 *
	 * @return true if there is an other instruction, false in others case
	 * @throws IOException
	 *             if the file closeReader while we read it, this exception may not happens.
	 */
    String getNext() throws IOException;

    /**
	 * Return the position of the execution pointer.
	 *
	 * @return the position of the execution pointer.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    long getExecutionPointer() throws IOException;


    /**
	 * Close the file when the reader finished him.
	 *
	 * @throws IOException
	 *             if file can't closeReader.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
    void closeReader() throws IOException, BracketsParseException;
    
    /**
	 * Mark.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    void mark() throws IOException;
    
    /**
	 * Reset.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
    void reset() throws IOException, BracketsParseException;
    
    /**
	 * Unmark.
	 *
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
    void unmark() throws BracketsParseException;

	/**
	 * Seek.
	 *
	 * @param pos
	 *            the pos
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void seek(long pos) throws IOException;
}