package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.interpreter.InInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class InInstructionTest {
	private ArgumentInstruction argumentInstruction;
	private Memory memory;
	private InInstruction instruction;
	private String filename;
	private String data;

	@Before
	public void setUp() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		data = "+++-++";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
			writer.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		BfReader reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new InInstruction();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write('a');
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		System.setIn(inputStream);
	}

	@Test
	public void in() throws Exception {
		instruction.execute(argumentInstruction);
		assertEquals('a', memory.get());
	}

	/**
	 * Simule empty file
	 * @throws Exception
	 */
	@Test(expected = net.brainfuck.exception.FileNotFoundIn.class)
	public void badIn() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		System.setIn(inputStream);
		instruction.execute(argumentInstruction);
	}

}