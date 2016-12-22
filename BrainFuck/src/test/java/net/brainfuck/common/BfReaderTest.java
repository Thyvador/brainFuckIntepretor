package net.brainfuck.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.brainfuck.io.BfReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * The Class BfReaderTest tests the <code>BfReader</code> interface.
 *
 * @author Francois Melkonian
 * @date 16/11/2016
 */
public class BfReaderTest {
	String filename;
	String data;


	/**
	 * Initialise a bf file.
	 *
	 * @throws Exception
	 *             the exception
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
	 * Check if each instructions written in file is read in the correct order.
	 *
	 * @return the next
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getNext() throws Exception {
		BfReader bfReader = new BfReader(filename);
		for (int i = 0; i < data.length(); i++) {
			assertEquals(data.charAt(i) + "", bfReader.getNext());
		}
	}

	/**
	 * Check içf the execution pointer is correct when the file is read.
	 *
	 * @throws Exception
	 *             the exception
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
	 * Test if the file can be written after closed.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = net.brainfuck.exception.IOException.class)
	public void closeReader() throws Exception {
		BfReader bfReader = new BfReader(filename);
		bfReader.closeReader();
		bfReader.getNext();
	}

	/**
	 * Test if commentary is omitted.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testS() throws Exception {
		String file = "filename.bf";
		data = "+#test\nINCR";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file), Charset.forName("UTF-8"))) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		BfReader bfReader = new BfReader(file);
		assertEquals("+", bfReader.getNext());
		assertEquals("INCR", bfReader.getNext());
	}
	
	/**
	 * Test if the space are omitted.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testSpace() throws Exception {
		String file = "filename.bf";
		data = "+#test\n  \t   INCR     ";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file), Charset.forName("UTF-8"))) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		BfReader bfReader = new BfReader(file);
		assertEquals("+", bfReader.getNext());
		assertEquals("INCR", bfReader.getNext());
	}

	/**
	 * Check if seek move the pointer to the given position.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void seek() throws Exception {
		BfReader reader = new BfReader(filename);
		reader.mark();
		String instruction = reader.getNext();
		reader.seek(1);
		assertEquals(instruction, reader.getNext());
	}

	/**
	 * Check if seek move the pointer to given position, even after read instructions.
	 *
	 * @throws Exception
	 *             the exception
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
		assertEquals(instruction, reader.getNext());
	}

	/**
	 * Test if mark can be preserved during reading a file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void mark() throws Exception {
		BfReader bfReader = new BfReader(filename);
		int mark = bfReader.getExecutionPointer();
		bfReader.mark();
		for (int i = 0; i < data.length(); i++) {
			bfReader.getNext();
		}
		bfReader.seek(mark);
		assertEquals(data.charAt(0) + "", bfReader.getNext());
	}

	/**
	 * Test if multiple marks can be preserved during reading a file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void marks() throws Exception {
		BfReader bfReader = new BfReader(filename);
		int mark = bfReader.getExecutionPointer();
		for (int i = 0; i < data.length(); i++) {
			bfReader.getNext();
			bfReader.mark();
		}
		bfReader.seek(mark);
		assertEquals(data.charAt(0) + "", bfReader.getNext());
	}


	/**
	 * Check if reset change the pointer on file.
	 *
	 * @throws Exception
	 *             the exception
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
	 * Check if reset go on last marks, even if the reader pointer has move before reset.
	 *
	 * @throws Exception
	 *             the exception
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
		assertEquals(instruction, reader.getNext());
	}

	/**
	 * Check if unmark delete marks.
	 *
	 * @throws Exception
	 *             the exception
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
	 * Check if the unmark delete the last mark.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void unmark2() throws Exception {
		BfReader reader = new BfReader(filename);
		for (int i = 0; i < data.length() / 2; i++) {
			reader.mark();
			reader.getNext();
		}
		int size = reader.getMarks().size();
		Integer last = reader.getMarks().get(size - 1);
		reader.unmark();
		assertTrue(!reader.getMarks().contains(last));
		assertEquals(size - 1, reader.getMarks().size());
	}

	/**
	 * Check if marks can be stacked.
	 *
	 * @throws Exception
	 *             the exception
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
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		new File(filename).delete();
		new File("filename.bf").delete();

	}
}