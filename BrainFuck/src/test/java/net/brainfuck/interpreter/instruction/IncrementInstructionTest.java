package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher
 */
public class IncrementInstructionTest {
	private Memory memory;
	private ExecutionReader reader;
	private IncrementInstruction instruction;
	private static String filename;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {


		List<Language> langage = Arrays.asList(Language.RIGHT,Language.RIGHT);

		reader = new ExecutionReader(langage, jumpTable);
		memory = new Memory();
		instruction = new IncrementInstruction();
	}

	/**
	 * Incr.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void incr() throws Exception {
		instruction.execute(memory);
		assertEquals(1, memory.get());
	}

	/**
	 * Over flow.
	 *
	 * @throws MemoryOverFlowException
	 *             the memory over flow exception
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	@Test(expected = MemoryOverFlowException.class)
	public void OverFlow() throws MemoryOverFlowException, MemoryOutOfBoundsException {
		for (int i = 0; i < 5000; i++) {
			instruction.execute(memory);
		}
	}

	/**
	 * Rewrite long.
	 *
	 * @throws Exception
	 *             the exception
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "INCR";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new IncrementInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("+", outputStream.toString());
	}
	 */

	/**
	 * Rewrite col.
	 *
	 * @throws Exception
	 *             the exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	@Test
	public void rewriteCol() throws Exception, FileNotFoundException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bmp";
		String data = "ffffff";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new IncrementInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("+", outputStream.toString());
	}
	 */

	/**
	 * Translate.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void translate() throws Exception {
		instruction = new IncrementInstruction();
		assertEquals("ffffff",instruction.translate() );
	}

}