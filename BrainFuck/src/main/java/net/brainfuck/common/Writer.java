package net.brainfuck.common;


import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * The Writer interface represents a writer.
 *
 * @author Jeremy Junac
 */
public interface Writer {

	/**
	 * Write the pixel.
	 *
	 * @param pixel
	 *            the pixel to write
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void write(int pixel) throws IOException;

	/**
	 * Write the pixel.
	 *
	 * @param hexaChain
	 *            the hexaCode of the pixel to write
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void write(String hexaChain) throws IOException;

	/**
	 * Close the writer.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	void close() throws net.brainfuck.exception.IOException, FileNotFoundException;

}
