package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher
 */
public class RightInstructionTest {

	ExecutionReader reader;
	Memory memory;
	RightInstruction instruction;

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {


		List<Language> langage = Arrays.asList(Language.RIGHT,Language.RIGHT);

		ExecutionReader reader = new ExecutionReader(langage);
		memory = new Memory();
		instruction = new RightInstruction();
	}

	/**
	 * Right.
	 *
	 */
	@Test
	public void right() throws Exception {
		instruction.execute(memory,reader);
		assertEquals(1, memory.getIndex());
	}

	/**
	 * Out of bound right.
	 *
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	@Test(expected = MemoryOutOfBoundsException.class)
	public void OutOfBoundRight() throws MemoryOutOfBoundsException {
		for (int i = 0; i < 30001; i++) {
			instruction.execute(memory,reader);
		}
	}

	/**
	 * Rewrite long.
	 *
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "RIGHT";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new RightInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals(">", outputStream.toString());
	}
	*/

	/**
	 * Rewrite col.
	 *
	 * @throws FileNotFoundException
	 *             the file not found exception
	@Test
	public void rewriteCol() throws Exception, FileNotFoundException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bmp";
		String data = "0000ff";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new RightInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals(">", outputStream.toString());
	}
	 */

	/**
	 * Translate.
	 *
	@Test
	public void translate() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "RIGHT";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new RightInstruction();
		assertEquals("0000ff",instruction.translate() );
	}
	 */

}