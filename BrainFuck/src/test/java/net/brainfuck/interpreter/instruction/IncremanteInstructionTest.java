package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.IncremanteInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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
	BfReader reader;
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
	@AfterClass
	public static void cleanUp() throws IOException {
		new File(filename).delete();
	}

}