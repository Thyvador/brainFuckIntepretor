package net.brainfuck.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author FranÃ§ois Melkonian
 * @date 16/11/2016
 */
public class BfImageReaderTest {
	BfImageReader reader;
	List<String> instructions;


	/**
	 * Initialise a bf image file
	 * @throws Exception
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
	 * Check if each instructions wrote in file is read in order.
	 * @throws Exception
	 */
	@Test
	public void getNext() throws Exception {
		reader = new BfImageReader("test.bmp");
		for (int i = 0; i < 3; i++) {
			assertEquals("Instruction n°" + i, reader.getNext(), instructions.get(i+1));
		}
	}

	/**
	 * Check if the reader return null where the file is fully read.
	 * @throws Exception
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
	 * The executer pointer is correct when the file is read
	 * @throws Exception
	 */
	@Test
	public void getExecutionPointer() throws Exception {
		for (int i = 0; i < instructions.size(); i++) {
			reader.getNext();
			assertEquals(i + 1, reader.getExecutionPointer());
		}
	}


	/**
	 * Test if we can write on file after we closed it.
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
	 * check if use reset before mark throw an error
	 */
	@Test(expected = net.brainfuck.exception.BracketsParseException.class)
	public void badReset() throws Exception {
		reader.reset();
	}

	/**
	 * Check if reset change the pointeur on file
	 * SPOILER : actuellement, ne le fait pas
	 */
	@Ignore
	@Test
	public void reset() throws Exception {
		for (int i = 0; i < instructions.size(); i++) {
			reader.mark();
			String instruction = reader.getNext();
			reader.reset();
			assertEquals(reader.getNext(), instruction);//TODO : Erreur
		}
	}

	/**
	 * Check if reset go on last marks, even if the reader pointer has move before reset
	 * @throws Exception
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
	 *  Check if unmark delete marks
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
	 * Check if marks can be stacked
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
	 * Check if seek move the pointer to the last mark.
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

	@After
	public void tearDown() throws Exception {
		new File("test.bmp").delete();

	}
}