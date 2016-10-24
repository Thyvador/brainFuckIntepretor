package net.brainfuck.common;

import java.io.FileReader;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * @author : Francois Melkonian
 */
public class BfReader implements Reader {
    private String next=null;
    private java.io.Reader reader;
    private static final int CR = 13;
    private static final int LF = 10;

    /**
     * Read 1-char-instruction file
     * Support long and short syntax
     *
     * @param filename name of the file
     * @throws FileNotFoundException if the file doesn't exist
     */
    public BfReader(String filename) throws FileNotFoundException {

        try {
            reader = new FileReader(filename);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(filename);
        }
    }


    /**
     * Read the line just after the pointer on the file
     * and put it on "next" string
     * @throws java.io.IOException if IO error, it will be catch in hasNext()
     */
    private void readUntilEndOfLine(int val) throws java.io.IOException {
        next = Character.toString((char) val);
        int c = reader.read();
        while (c != CR && c!= -1) {
            next += Character.toString((char) c);
            c = reader.read();
        }
    }

    /**
     * Skip new line character(s)
     * End of line character change according OS.
     *
     * @return The first character of the line
     */
    private int ignoreNewLineChar() throws java.io.IOException {
        int c;
        c = reader.read();
        if (c == CR) {
            c = reader.read();
        }
        if (c == LF) {
            c = reader.read();
        }
        return c;
    }

    /**
     * Read the file to see if there is an other instruction.
     *
     * @return true if there is an other instruction, false in others case
     * @throws IOException if file close during reading
     */
    public String getNext() throws IOException {
        int nextVal;
        try {
            nextVal = reader.read();
            if(next==null&&isLong(nextVal)){
                readUntilEndOfLine(nextVal);
                return next;
            }else if(isNewLine(nextVal)){
                nextVal=ignoreNewLineChar();
                if(isLong(nextVal)){
                    readUntilEndOfLine(nextVal);
                    return next;
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException();
        }
        if (nextVal == -1) {
            return null;
        }
        next = Character.toString((char) nextVal);
        return next;
    }

    private boolean isNewLine(int nextVal) {
        return nextVal == CR || nextVal == LF;
    }

    private boolean isLong(int nextVal) {
        return nextVal >= 'B' && nextVal <= 'R';
    }


    /**
     * Close the file when the reader finished him
     *
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
