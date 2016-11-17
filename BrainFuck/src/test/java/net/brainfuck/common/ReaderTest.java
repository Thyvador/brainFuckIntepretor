package net.brainfuck.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Francois Melkonian
 * @date 16/11/2016
 */
public class ReaderTest {
	String filename;
	String data;


	/**
	 * Initialise a bf file
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		data = "+++-++";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	/**
	 * Each instructions wrote in file is read in order.
	 * @throws Exception
	 */
	@Test
	public void getNext() throws Exception {
		BfReader bfReader = new BfReader(filename);
		for (int i = 0; i < data.length(); i++) {
			assertEquals(data.charAt(i) + "", bfReader.getNext());
		}
	}

	/**
	 * The executer pointer is correct when the file is read
	 * @throws Exception
	 */
	@Test
	public void getExecutionPointer() throws Exception {
		BfReader bfReader = new BfReader(filename);
		for (int i = 0; i < data.length(); i++) {
			assertEquals(i, bfReader.getExecutionPointer());
			bfReader.getNext();
		}
	}

	/**
	 * Test if we can write on file after we closed it.
	 */
	@Test(expected = net.brainfuck.exception.IOException.class)
	public void closeReader() throws Exception {
		BfReader bfReader = new BfReader(filename);
		bfReader.closeReader();
		bfReader.getNext();
	}

	/**
	 * Check if seek move the pointer to the last mark.
	 */
	@Test
	public void seek() throws Exception {
		BfReader reader = new BfReader(filename);
		reader.mark();
		String instruction = reader.getNext();
		reader.seek(1);
		assertEquals(instruction,reader.getNext());
	}

	/**
	 * Check if seek move the pointer to the last mark, even after read instructions.
	 */
	@Test
	public void seek2() throws Exception {
		BfReader reader = new BfReader(filename);
		reader.mark();
		String instruction = reader.getNext();
		for (int i = 0; i < 5; i++) {
			reader.getNext();
		}
		reader.seek(1);
		assertEquals(instruction,reader.getNext());
	}
	/**
	 * Test if mark can be preserved during the read of a file
	 */
	@Test
	public void mark() throws Exception {
		BfReader bfReader = new BfReader(filename);
		long mark = bfReader.getExecutionPointer();
		bfReader.mark();
		for (int i = 0; i < data.length(); i++) {
			bfReader.getNext();
		}
		bfReader.seek(mark);
		assertEquals(data.charAt(0) + "", bfReader.getNext());
	}

	/**
	 * Test if multiple marks can be preserved during the read of a file
	 */
	@Test
	public void marks() throws Exception {
		BfReader bfReader = new BfReader(filename);
		long mark = bfReader.getExecutionPointer();
		for (int i = 0; i < data.length(); i++) {
			bfReader.getNext();
			bfReader.mark();
		}
		bfReader.seek(mark);
		assertEquals(data.charAt(0) + "", bfReader.getNext());
	}


	/**
	 * Check if reset change the pointeur on file
	 */
	@Test
	public void reset() throws Exception {

		BfReader bfReader = new BfReader(filename);
		bfReader.mark();
		bfReader.getNext();
		double pointer = bfReader.getExecutionPointer();
		bfReader.mark();
		bfReader.getNext();
		bfReader.reset();
		assertSame((int) pointer, (int) bfReader.getExecutionPointer());

	}

	/**
	 * Check if reset go on last marks, even if the reader pointer has move before reset
	 * @throws Exception
	 */
	@Test
	public void reset2s() throws Exception {
		BfReader reader = new BfReader(filename);
		reader.mark();
		String instruction = reader.getNext();
		for (int i = 0; i < 5; i++) {
			reader.getNext();
		}
		reader.reset();
		assertEquals(instruction,reader.getNext());
	}

	/**
	 *  Check if unmark delete marks
	 */
	@Test
	public void unmark() throws Exception {
		BfReader reader = new BfReader(filename);
		for (int i = 0; i < data.length(); i++) {
			reader.mark();
			reader.unmark();
			assertTrue(reader.getMarks().size() == 0);

		}
	}
	/**
	 *  Check if the unmark delete the last mark
	 */
	@Test
	public void unmark2() throws Exception {
		BfReader reader = new BfReader(filename);
		for (int i = 0; i < data.length()/2; i++) {
			reader.mark();
			reader.getNext();
		}
		int size = reader.getMarks().size();
		Long last = reader.getMarks().get(size-1);
		reader.unmark();
		assertTrue(!reader.getMarks().contains(last));
		assertEquals(size - 1,reader.getMarks().size());
	}

	/**
	 * Check if marks can be stacked
	 */
	@Test
	public void unmark3() throws Exception {
		BfReader reader = new BfReader(filename);
		for (int i = 0; i < data.length(); i++) {
			reader.mark();
		}
		for (int i = 0; i < data.length(); i++) {
			reader.unmark();
		}
		assertTrue(reader.getMarks().size() == 0);
	}

	/**
	 * Delete the file used for test.
	 */
	@After
	public void tearDown() throws Exception {
		File file = new File(filename);
		file.delete();

	}
}