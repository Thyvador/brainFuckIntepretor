package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.Exception;
import net.brainfuck.interpreter.InInstruction;
import net.brainfuck.interpreter.JumpInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.ws.soap.Addressing;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class JumpInstructionTest {
	private ArgumentInstruction argumentInstruction;
	private Memory memory;
	private Reader reader;
	private JumpInstruction instruction;
	String filename;
	String data;

	@Before
	public void setUp() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		data = ">+[++]--[]";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new JumpInstruction();
	}

	//La case mémoire est à 0.
	@Test
	public void jump() throws Exception {
		reader.seek(3);
		instruction.execute(argumentInstruction);
		assertEquals(6, reader.getExecutionPointer());
	}

	@Test
	public void doNotJump() throws Exception {
		memory.set(3);
		reader.seek(3);
		instruction.execute(argumentInstruction);
		assertEquals(3, reader.getExecutionPointer());
	}

	@After
	public void tearDown() throws Exception {
		new File(filename).delete();

	}

	@Ignore
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "JUMP";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new JumpInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("[", outputStream.toString());
	}

	@Ignore
	@Test
	public void rewriteCol() throws Exception, FileNotFoundException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bmp";
		String data = "ff7f00";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new JumpInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("[", outputStream.toString());
	}

	@Ignore
	@Test
	public void translate() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "JUMP";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new JumpInstruction();
		assertEquals("ff7f00",instruction.translate() );
	}
}