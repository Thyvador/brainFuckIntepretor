package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.interpreter.JumpInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	private BfReader reader;
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
}