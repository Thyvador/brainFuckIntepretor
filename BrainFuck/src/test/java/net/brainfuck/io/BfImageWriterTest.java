package net.brainfuck.io;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * BfImageWriterTest tests the <code>BfImageWriter</code> class.
 * @author Alexandre Hiltcher
 */
public class BfImageWriterTest {

	private BfImageWriter writer;
	private static String filename;

	/**
	 * Sets the writer for following tests.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@Before
	public void setUp() throws IOException, FileNotFoundException, java.io.FileNotFoundException {
		filename ="test.bmp";
		writer = new BfImageWriter(new FileOutputStream(filename));

	}

	/**
	 * Check if the file cannot be written when writer closed.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = IOException.class)
	public void close() throws Exception {
		writer.close();
		writer.write(0x000111);
	}

	/**
	 * Clean up.
	 *
	 * @throws java.io.IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@AfterClass
	public static void cleanUp() throws java.io.IOException {
		new File(filename).delete();
	}
}