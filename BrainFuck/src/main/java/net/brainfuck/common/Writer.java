package net.brainfuck.common;

import java.io.IOException;

public interface Writer {
	
	public void write(int pixel) throws IOException;
	
	public void close() throws IOException;

	void write(String hexaChain) throws IOException;
	
}
