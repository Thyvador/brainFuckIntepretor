package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.DecrementInstruction;
import net.brainfuck.interpreter.IncremanteInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class IncremanteInstructionTest {
	ArgumentInstruction argumentInstruction;
	Memory memory;
	Reader reader;
	IncremanteInstruction instruction;
	private static String filename;

	@Before
	public void setUp() throws Exception {

		filename = "filename.bf";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),  Charset.forName("UTF-8"))) {
			writer.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		BfReader reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new IncremanteInstruction();
	}

	@Test
	public void incr() throws Exception {
		instruction.execute(argumentInstruction);
		assertEquals(1, memory.get());
	}

	@Test(expected = MemoryOverFlowException.class)
	public void OverFlow() throws MemoryOverFlowException, MemoryOutOfBoundsException {
		for (int i = 0; i < 5000; i++) {
			instruction.execute(argumentInstruction);
		}
	}

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
		instruction = new IncremanteInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("+", outputStream.toString());
	}

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
		instruction = new IncremanteInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("+", outputStream.toString());
	}

	@Test
	public void translate() throws Exception {
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
		instruction = new IncremanteInstruction();
		assertEquals("ffffff",instruction.translate() );
	}

	@AfterClass
	public static void cleanUp() throws IOException {
		new File(filename).delete();
	}

}