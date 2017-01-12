package net.brainfuck.io;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;

/**
 *
 * Reader interface used to read bf instruction in "short" , "long" and picture format.
 *
 * @author FoBar Team
 */
public interface Reader {

    /**
	 * Read the file and return the next instruction.
	 *
	 * @return the next instruction, null if the EOF is reached.
	 * @throws IOException
	 *             If an I/O exception occurs.
	 */
    String getNext() throws IOException;

    /**
	 * Return the position of the execution pointer.
	 *
	 * @return the position of the execution pointer.
	 * @throws IOException
	 *             If an I/O exception occurs.
	 */
    int getExecutionPointer() throws IOException;


    /**
	 * Close the file when the reader finished him.
	 *
	 * @throws IOException
	 *             If the reader can't be closed because an I/O exception occurs.
	 * @throws BracketsParseException
	 *             If a brackets exception occurs.
	 */
    void closeReader() throws IOException, BracketsParseException;
    
    /**
	 * Mark the current position of the execution pointer.
	 *
	 * @throws IOException
	 *             If an I/O exception occurs.
	 */
    void mark() throws IOException;
    
    /**
	 * Set the exception pointer to last position marked.
	 *
	 * @throws IOException
	 *             If an I/O exception occurs.
	 * @throws BracketsParseException
	 *             If a bracket exception occurs.
	 */
    void reset() throws IOException, BracketsParseException;
    
    /**
	 * Unmark the last marked instruction.
	 *
	 * @throws BracketsParseException
	 *             If a bracket exception occurs.
	 */
    void unmark() throws BracketsParseException;

	/**
	 * Set trhe execution pointer to the position.
	 *
	 * @param pos
	 *            the posistion to seek.
	 * @throws IOException
	 *             If an I/O exception occurs.
	 */
	void seek(int pos) throws IOException;
}