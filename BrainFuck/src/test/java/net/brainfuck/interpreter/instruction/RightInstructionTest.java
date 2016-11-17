package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.JumpTable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.brainfuck.interpreter.RightInstruction;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class RightInstructionTest {
	ArgumentInstruction argumentInstruction;
	Memory memory;
	BfReader reader;
	RightInstruction instruction;
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
		instruction = new RightInstruction();
	}

	@Test
	public void right() throws Exception {
		instruction.execute(argumentInstruction);
		assertEquals(1, memory.getIndex());
	}

	@Test(expected = MemoryOutOfBoundsException.class)
	public void OutOfBoundRight() throws MemoryOutOfBoundsException {
		for (int i = 0; i < 30000; i++) {
			instruction.execute(argumentInstruction);
		}
	}
	@AfterClass
	public static void cleanUp() throws IOException {
		new File(filename).delete();
	}

}