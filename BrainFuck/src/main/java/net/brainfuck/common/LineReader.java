package net.brainfuck.common;


import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

import java.io.BufferedReader;


public class LineReader implements Reader {
    @SuppressWarnings("unused")
	private String filename;
    private String next;
    private BufferedReader reader;


    public LineReader(String filename) throws FileNotFoundException {
        this.filename = filename;
        try {
            reader = new BufferedReader(new java.io.FileReader(filename));
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("file '"+filename+"' not found !");
        }
    }

    @Override
    public char decode() {
        return 0;
    }


    /**
     * Read the file to see if there is an other instruction
     *
     * @return true if there is an other instruction, false in others case
     * @throws IOException if file close during reading
     */
    public boolean hasNext() throws IOException {
        try {
            next = reader.readLine();
        } catch (java.io.IOException e) {
            throw new IOException("file closed while reading ");
        }
        return next != null;
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
