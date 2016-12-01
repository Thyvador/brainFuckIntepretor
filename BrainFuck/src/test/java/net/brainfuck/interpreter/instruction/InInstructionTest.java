package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.Exception;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
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
public class InInstructionTest {
	private Memory memory;
	private InInstruction instruction;
	private String filename;
	private ExecutionReader reader;

	/**
	 * Sets the up.
	 *
	@Before
	public void setUp() throws Exception {


		List<Language> langage = Arrays.asList(Language.RIGHT,Language.RIGHT);

		reader = new ExecutionReader(langage);
		memory = new Memory();
		instruction = new InInstruction(null);

	}
	 */

	/**
	 * In.
	 *
	@Test
	public void in() throws Exception {
		instruction.execute(memory,reader);
		assertEquals('a', memory.get());
	}
	 */

	/**
	 * Simule empty file.
	 * An FileNotFoundIn Exception may happens.
	 *
	@Test(expected = net.brainfuck.exception.FileNotFoundIn.class)
	public void badIn() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		System.setIn(inputStream);
		instruction.execute(memory,reader);
	}
	 */

// TODO : test ici
	/**
	 * Rewrite long.
	 *
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "IN";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new InInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals(",", outputStream.toString());
	}
	 */

	/**
	 * Rewrite col.
	 *
	@Test
	public void rewriteCol() throws Exception, FileNotFoundException {
		filename = "filename.bmp";
		String data = "ffff00";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new InInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals(",", outputStream.toString());
	}
	 */

	/**
	 * Translate.
	 *
	 *
	 */
	@Test
	public void translate() throws Exception {
		instruction = new InInstruction(null);
		assertEquals("ffff00",instruction.translate() );
	}


}