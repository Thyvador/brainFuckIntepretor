package net.brainfuck.common;


import net.brainfuck.common.exception.IOException;

public interface Reader {
	    char decode();
	    boolean hasNext() throws IOException;
	   	String getNext();
	    void close() throws IOException;
}