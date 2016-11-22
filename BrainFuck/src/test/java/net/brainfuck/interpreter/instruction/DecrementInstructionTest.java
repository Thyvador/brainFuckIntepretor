package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.DecrementInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

// TODO: Auto-generated Javadoc
/**
 * Created by Alexandre on 16/11/2016.
 */
public class DecrementInstructionTest {
	ArgumentInstruction argumentInstruction;
	Memory memory;
	Reader reader;
	DecrementInstruction instruction;
	private static String filename;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
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
		instruction = new DecrementInstruction();
	}

	/**
	 * Decr.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void decr() throws Exception {
		memory.set(50);
		instruction.execute(argumentInstruction);
		assertEquals(49, memory.get());
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
		instruction.execute(argumentInstruction);
	}

	/**
	 * Rewrite long.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "DECR";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new DecrementInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("-", outputStream.toString());
	}

	/**
	 * Rewrite col.
	 *
	 * @throws Exception
	 *             the exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@Test
	public void rewriteCol() throws Exception, FileNotFoundException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bmp";
		String data = "4b0082";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new DecrementInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("-", outputStream.toString());
	}

	/**
	 * Translate.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void translate() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "DECR";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new DecrementInstruction();
		assertEquals("4b0082",instruction.translate() );
	}



	/**
	 * Clean up.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@AfterClass
	public static void cleanUp() throws IOException {
		new File("filename.bf").delete();
		new File("filename.log").delete();
		new File("test.bmp").delete();
	}
}