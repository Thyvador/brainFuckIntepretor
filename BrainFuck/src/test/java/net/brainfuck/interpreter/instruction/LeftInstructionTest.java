package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.moveinstruction.LeftInstruction;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher
 */
public class LeftInstructionTest {
	AbstractInstruction argumentInstruction;
	Memory memory;
	ExecutionReader reader;
	LeftInstruction instruction;
	private String filename;

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {
		List<Language> langage = Arrays.asList(Language.LEFT,Language.LEFT);

		reader = new ExecutionReader(langage);
		memory = new Memory();
		instruction = new LeftInstruction();
	}

	/**
	 * Left.
	 *
	 */
	@Test
	public void left() throws Exception {
		memory.right();
		memory.right();
		instruction.execute(memory);
		assertEquals(1, memory.getIndex());
	}

	/**
	 * Out of bound left.
	 *
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	@Test(expected = MemoryOutOfBoundsException.class)
	public void OutOfBoundLeft() throws Exception {
		instruction.execute(memory);

	}

//TODO : Test mis en pause
	/**
	 * Rewrite long.
	 *
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "LEFT";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new LeftInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("<", outputStream.toString());
	}
	 */

	/**
	 * Rewrite col.
	 *
	@Test
	public void rewriteCol() throws Exception, FileNotFoundException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bmp";
		String data = "9400d3";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new LeftInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("<", outputStream.toString());
	}
	 */

	/**
	 * Translate.
	 *
	 */
	@Test
	public void translate() throws Exception {
		instruction = new LeftInstruction();
		assertEquals("9400d3",instruction.translate() );
	}

}