package net.brainfuck.common;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;

/**
 * Created by François MELKONIAN on 28/09/2016.
 * Reader interface used to read bf instruction in "short" , "long" and picture format.
 */
public interface Reader {

    /**
     * Read the file to see if there is an other instruction
     *
     * @return true if there is an other instruction, false in others case
     * @throws IOException if the file close while we read it, this exception may not happens.
     */
    String getNext() throws IOException;


    /**
     * Close the file when the reader finished him.
     *
     * @throws IOException if file can't close.
     * @throws BracketsParseException 
     */
    void close() throws IOException, BracketsParseException;
    
    void mark() throws IOException;
    
    void reset() throws IOException, BracketsParseException;
    
    void unmark() throws BracketsParseException;
}