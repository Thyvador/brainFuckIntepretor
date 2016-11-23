package net.brainfuck.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The Class BfImageReaderTest tests the <code>BfImageReader</code> class.
 *
 * @author Francois Melkonian
 */
public class BfImageReaderTest {
	private BfImageReader reader;
	private List<String> instructions;


	/**
	 * Initialise a bf image file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		BfImageWriter writer = new BfImageWriter(new FileOutputStream("test.bmp"));
		instructions = Arrays.asList("0000ff", "0000ff", "0000ff", "0000ff", "000000", "000000","0000ff", "0000ff", "0000ff", "000000", "000000", "000000","0000ff", "0000ff", "0000ff", "000000", "000000", "00ff00");
			for (int i = 0; i < instructions.size(); i++) {
				writer.write(instructions.get(i));
			}
		writer.close();
		reader = new BfImageReader("test.bmp");
	}


	/**
	 * Check if each instructions written in the file is read correctly.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getNext() throws Exception {
		reader = new BfImageReader("test.bmp");
		for (int i = 0; i < 3; i++) {
			assertEquals("Instruction n°" + i, reader.getNext(), instructions.get(i+1));
		}
	}

	/**
	 * Check if the reader return null when the file is fully read.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getNext2() throws Exception {
		reader = new BfImageReader("test.bmp");
		for (int i = 0; i < instructions.size(); i++) {
			reader.getNext();
		}
		assertEquals(reader.getNext(), null);
	}
	
	/**
	 * Test if the execution pointer is correct when the file is read.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getExecutionPointer() throws Exception {
		for (int i = 0; i < instructions.size(); i++) {
			reader.getNext();
			assertEquals(i + 1, reader.getExecutionPointer());
		}
	}


	/**
	 * Test if file can be written when closed.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void mark() throws Exception {
		for (int i = 0; i < instructions.size(); i++) {
			reader.getNext();
			reader.mark();
			assertTrue(reader.getMarks().contains(new Long(i + 1)));
		}
	}

	/**
	 * Check if use reset before mark throw an error.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = net.brainfuck.exception.BracketsParseException.class)
	public void badReset() throws Exception {
		reader.reset();
	}

	/**
	 * Check if reset change the pointer in the file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void reset() throws Exception {
		for (int i = 0; i < instructions.size(); i++) {
			reader.mark();
			String instruction = reader.getNext();
			reader.reset();
			assertEquals(i+1,reader.getMarks().size());
		}
	}

	/**
	 * Check if reset go on last marks, even if the reader pointer has move before reset.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void reset2s() throws Exception {
		reader.mark();
		String instruction = reader.getNext();
		for (int i = 0; i < 5; i++) {
			reader.getNext();
		}
		reader.reset();
		assertEquals(instruction,reader.getNext());
	}


	/**
	 * Check if unmark delete marks.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void unmark() throws Exception {
		for (String instruction: instructions ) {
			reader.mark();
			reader.unmark();
			assertTrue(reader.getMarks().size() == 0);
		}
	}

	/**
	 * Check if marks can be stacked.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void unmark2() throws Exception {
		for (String instruction: instructions ) {
			reader.mark();
		}
		for (String instruction: instructions ) {
			reader.unmark();
		}
		assertTrue(reader.getMarks().size() == 0);
	}

	/**
	 * Check if seek move the pointer to the given position.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void seek() throws Exception {
		reader.mark();
		String instruction = reader.getNext();
		reader.seek(1);
		assertEquals(instruction,reader.getNext());
	}

	/**
	 * Check if seek move the pointer to the last mark, even after read instructions.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void seek2() throws Exception {
		reader.mark();
		String instruction = reader.getNext();
		for (int i = 0; i < 5; i++) {
			reader.getNext();
		}
		reader.seek(1);
		assertEquals(instruction,reader.getNext());
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		new File("test.bmp").delete();

	}
}