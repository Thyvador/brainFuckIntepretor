package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.interpreter.BackInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
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
	private BfReader reader;
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


	@After
	public void tearDown() throws Exception {
		new File(filename).delete();

	}
}