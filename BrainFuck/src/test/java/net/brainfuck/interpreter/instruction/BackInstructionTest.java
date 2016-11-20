package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.interpreter.BackInstruction;
import net.brainfuck.interpreter.DecrementInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Categories;

import java.io.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author Alexandre,Francois Melkonian
 */
public class BackInstructionTest {
	private ArgumentInstruction argumentInstruction;
	private Memory memory;
	private Reader reader;
	private BackInstruction instruction;
	private String filename;


	/**
	 * Create a
	 */
	@Before
	public void setUp() throws Exception {

		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = ">+[++]--[]";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new BackInstruction();
	}

	//La case mémoire et à 0, ca passe.
	@Test
	public void back() throws Exception {
		reader.seek(6);
		instruction.execute(argumentInstruction);
		assertEquals(6, reader.getExecutionPointer());
	}

	@Test
	public void doNotBack() throws Exception {
		memory.set(2);
		reader.seek(6);
		instruction.execute(argumentInstruction);
		assertEquals(3, reader.getExecutionPointer());
	}


	@Test (expected = BracketsParseException.class)
	public void parenthizingError() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "]";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new BackInstruction();
		instruction.execute(argumentInstruction);

	}

	@Ignore ("Probleme de check")
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "BACK";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("]", outputStream.toString());
	}

	@Test
	public void rewriteCol() throws Exception, java.io.FileNotFoundException {
		filename = "filename.bmp";
		String data = "4b0082";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("-", outputStream.toString());
	}


	@Ignore ("Probleme de check")
	@Test
	public void translate() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "BACK";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assertEquals("ff0000", instruction.translate());
	}

	@After
	public void tearDown() throws Exception {
		new File(filename).delete();

	}
}