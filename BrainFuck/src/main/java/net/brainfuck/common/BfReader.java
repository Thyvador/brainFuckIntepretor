package net.brainfuck.common;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

import java.io.File;
import java.io.FileReader;

/**
 * author : francois Melkonian
 */
public class BfReader implements Reader {
    private String filename;
    private char next;
    private java.io.FileReader reader;

    /**
     * Read 1-char-instruction file
     * @param filename name of the file
     * @throws FileNotFoundException if the file doesn't exist
     */
    public BfReader(String filename) throws FileNotFoundException, java.io.FileNotFoundException {
        this.filename = filename;
        if (!((new File(filename)).exists())){
            throw new FileNotFoundException(filename);
        }
        reader = new FileReader(filename);
    }

    /**
     * Work in progress
     * @return 0
     */
    @Override
    public char decode() {
        return 0;
    }

    /**
     * Read the file to see if there is an other instruction
     *
     * @return true if there is an other instruction, false in others case
     * @throws IOException if IO errors
     */
    public boolean hasNext() throws IOException {
        int nextVal = 0;
        try {
            nextVal = reader.read();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
        if (nextVal == -1) {
            return false;
        }
        next = (char) nextVal;
        return true;
    }

    /**
     * Get the next instruction
     * @return the next instruction
     */
    public String getNext() {
        return Character.toString(next);
    }

    /**
     * Close the file when the reader finished him
     * @throws IOException if file can't close
     */
    public void close() throws IOException {
        try {
            reader.close();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }


}
