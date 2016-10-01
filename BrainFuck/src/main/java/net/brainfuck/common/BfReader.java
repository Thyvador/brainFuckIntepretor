package net.brainfuck.common;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

/**
 * author : francois Melkonian
 */
public class BfReader implements Reader {
    private String next;
    private java.io.FileReader reader;

    /**
     * Read 1-char-instruction file
     * @param filename name of the file
     * @throws FileNotFoundException if the file doesn't exist
     */
    public BfReader(String filename) throws FileNotFoundException, java.io.FileNotFoundException {
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
     * Read the line just after the pointer on the file
     *
     * @return the line read
     * @throws IOException if IO error, it will be catch in hasNext()
     */
    private String readUntilEndOfLine() throws java.io.IOException {
        String line ="";
        int c = reader.read();
        while(c!=13){
            line += Character.toString((char)c);
            c=reader.read();
        }
        return line;
    }

    /**
     * Skip new line character(s)
     * End of line character change according OS.
     * @return The first character of the line
     */
    private int ignoreNewLineChar() {
        int c = 0;
        try {
            c = reader.read();
            if(c == 13){
                c = reader.read();
            }
            if(c == 10){
                c = reader.read();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return c;
    }
    /**
     * Read the file to see if there is an other instruction.
     *
     * @return true if there is an other instruction, false in others case
     * @throws IOException if file close during reading
     */
    public boolean hasNext() throws IOException {
        int nextVal = 0;
        try {
            nextVal = reader.read();
            if(nextVal == 13||nextVal==10){
                nextVal = ignoreNewLineChar();
                next = Character.toString((char)nextVal) ;
                if(nextVal>='B'&&nextVal<='R'){// Line command detected
                    next += readUntilEndOfLine();
                }
                return true;
            }
        } catch (java.io.IOException e) {
            throw new IOException();
        }
        if (nextVal == -1) {
            return false;
        }
        next = Character.toString((char)nextVal);
        return true;
    }

    /**
     * Get the next instruction
     * @return the next instruction
     */
    public String getNext() {
        return next;
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
