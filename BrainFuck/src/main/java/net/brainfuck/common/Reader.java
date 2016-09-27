package main.java.net.brainfuck.common;


import main.java.net.brainfuck.common.exception.IOException;

public interface Reader {
	    public char decode();
	    public boolean hasNext() throws IOException, java.io.IOException;
	    public char getNext();
	    public void close() throws IOException;
}