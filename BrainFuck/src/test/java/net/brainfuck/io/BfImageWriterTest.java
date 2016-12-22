package net.brainfuck.io;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.io.BfImageWriter;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;

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
		byte[] imageArray = new byte[]{
				0x42, 0x4d, 0x5a, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x36, 0x00, 0x00, 0x00, 0x28, 0x00
				, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x01, 0x00, 0x18, 0x00, 0x00, 0x00
				, 0x00, 0x00, 0x24, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
				, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00
				, 0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00, (byte) 0xff, (byte) 0xff
				, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00};

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