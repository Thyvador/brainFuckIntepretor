package net.brainfuck.common;


import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

import java.io.BufferedReader;


public class LineReader implements Reader {
    private String filename;
    private String next;
    private BufferedReader reader;

    public LineReader(String filename) throws FileNotFoundException {
        this.filename = filename;
        try {
            reader = new BufferedReader(new java.io.FileReader(filename));
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public char decode() {
        return 0;
    }

    public boolean hasNext() throws IOException {
        try {
            next = reader.readLine();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
        if (next == null) {
            return false;
        }
        return true;
    }

    public String getNext() {
        return next;
    }

    public void close() throws IOException {
        try {
            reader.close();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }


}
