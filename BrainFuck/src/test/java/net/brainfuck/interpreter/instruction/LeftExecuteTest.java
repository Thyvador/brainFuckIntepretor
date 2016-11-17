package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.LeftExecute;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class LeftExecuteTest {
	ArgumentInstruction argumentInstruction;
	Memory memory;
	BfReader reader;
	LeftExecute instruction;
	private String filename;

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
		instruction = new LeftExecute();
	}

	@Test
	public void left() throws Exception {
		memory.right();
		memory.right();
		instruction.execute(argumentInstruction);
		assertEquals(1, memory.getIndex());
	}

	@Test(expected = MemoryOutOfBoundsException.class)
	public void OutOfBoundLeft() throws MemoryOutOfBoundsException {
		instruction.execute(argumentInstruction);

	}
}