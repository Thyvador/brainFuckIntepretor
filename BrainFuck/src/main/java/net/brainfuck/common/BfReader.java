package net.brainfuck.common;

import java.io.FileReader;

import net.brainfuck.exception.CharacterException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * Read file with long and short syntax blended.
 *
 * @author : Francois Melkonian
 */
public class BfReader implements Reader {
    private String next = null;
    private java.io.Reader reader;
    private boolean firstLine = true;
    private static final int CR = '\r';
    private static final int LF = '\n';
    private int oldvar;

    /**
     * Constructs a BfReader from file name.
     * Support long and short syntax
     *
     * @param filename name of the file.
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
     * and put it on "next" string.
     *
     *
     * @param val the current value
     * @throws java.io.IOException if IO error, it will be catch in getNext().
     */
    private void readUntilEndOfLine(int val) throws java.io.IOException {
        next = Character.toString((char) val);
        int c = reader.read();
        while (c != LF && c != CR && c != -1) {
            next += Character.toString((char) c);
            c = reader.read();
        }
        oldvar = c;
    }

    /**
     * Skip new line character(s).
     * End of line character change according OS.
     *
     * @return The first character of the line.
     * @throws java.io.IOException if IO error, it will be catch in getNext().
     */
    private int ignoreNewLineChar() throws java.io.IOException {
        int c;
        while (isNewLine(c = reader.read())) ;
        return c;
    }

    /**
     * Get the next instruction.
     *
     * @return the next instruction.
     * @throws IOException if file close during reading.
     */
    @Override
    public String getNext() throws IOException {
        int nextVal;
        try {
            nextVal = reader.read();
            if (isNewLine(nextVal)) {
                nextVal = ignoreNewLineChar();
                oldvar = LF;
            }
            if (nextVal == -1) {
                return null;
            }
            if (isLong(nextVal)) {
                if (firstLine) {
                    firstLine = false;
                    readUntilEndOfLine(nextVal);
                    oldvar = LF;
                    return next;
                } else if (oldvar == LF) {
                    readUntilEndOfLine(nextVal);
                    oldvar = LF;
                    return next;
                } else {
                    oldvar = nextVal;
                    return Character.toString((char) nextVal);
                }
            } else {
                if(firstLine){
                    firstLine = false;
                }
                oldvar = nextVal;
                return Character.toString((char) nextVal);
            }
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }

    /**
     * Check if a character is a '\n' or '\r'.
     *
     * @param nextVal the character to test.
     * @return true if the char is an end of line char, else false.
     */
    private boolean isNewLine(int nextVal) {
        return nextVal == CR || nextVal == LF;
    }

    /**
     * Check if the character start a "long" syntax or not.
     *
     * @param nextVal the first character of a line.
     * @return true if the line may contain a "long" syntax.
     */
    private boolean isLong(int nextVal) {
        return nextVal >= 'B' && nextVal <= 'R';
    }


    /**
     * Close the file when the reader finished him.
     *
     * @throws IOException if file can't close.
     */
    @Override
    public void close() throws IOException {
        try {
            reader.close();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }
}
