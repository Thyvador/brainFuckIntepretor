package net.brainfuck.common;
import net.brainfuck.exception.IOException;

/**
 * Created by François MELKONIAN on 28/09/2016.
 * Reader interface used to read bf instruction in "short" , "long" and picture format.
 */
public interface Reader {
    char decode();

    /**
     * Read the file to see if there is an other instruction
     *
     * @return true if there is an other instruction, false in others case
     * @throws IOException
     */
    boolean hasNext() throws IOException;

    /**
     * Get the next instruction
     * @return the next instruction
     */
    String getNext();

    void close() throws IOException;
}