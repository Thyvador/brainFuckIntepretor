package net.brainfuck.common;


import net.brainfuck.common.exception.IOException;

public interface Reader {
	    public char decode();
	    public boolean hasNext() throws IOException;
	    public char getNext();
	    public void close() throws IOException;
}