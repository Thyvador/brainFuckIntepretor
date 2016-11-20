package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.*;
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

	@Test
	public void rewriteLong() throws Exception, IOException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "JUMP\nBACK";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		Executer executer = new Executer(new ArgumentAnalyzer(new String[]{"-p", "filename.bf"}));
		JumpTable jumpTable = new BfCompiler(reader).compile(executer.getContextExecuters()).getSecond();
		argumentInstruction = new ArgumentInstruction(memory, reader, jumpTable);
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		reader.getNext();
		instruction.rewrite();
		assertEquals("]", outputStream.toString());
	}

	@Test
	public void rewriteCol() throws Exception, IOException {
		filename = "filename.bmp";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write("ff7f00");
		writer.write("4b0082");
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		Executer executer = new Executer(new ArgumentAnalyzer(new String[]{"-p", "filename.bmp"}));
		JumpTable jumpTable = new BfCompiler(reader).compile(executer.getContextExecuters()).getSecond();
		argumentInstruction = new ArgumentInstruction(memory, reader, jumpTable);
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		reader.getNext();
		instruction.rewrite();
		assertEquals("]", outputStream.toString());
	}


	@Test
	public void translate() throws Exception, IOException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "JUMP\nBACK";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		Executer executer = new Executer(new ArgumentAnalyzer(new String[]{"-p", "filename.bf"}));
		JumpTable jumpTable = new BfCompiler(reader).compile(executer.getContextExecuters()).getSecond();
		argumentInstruction = new ArgumentInstruction(memory, reader, jumpTable);
		reader.getNext();
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assertEquals("ff0000", instruction.translate());
	}

	@After
	public void tearDown() throws Exception {
		new File(filename).delete();

	}
}