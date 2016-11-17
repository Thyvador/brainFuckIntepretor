package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.OutInstruction;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class OutInstructionTest {
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private ArgumentInstruction argumentInstruction;
	private Memory memory;
	private OutInstruction instruction;
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
		instruction = new OutInstruction();
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	public void out() throws Exception {
		memory.set('a');
		instruction.execute(argumentInstruction);
		assertEquals("a", outputStream.toString());
	}

}