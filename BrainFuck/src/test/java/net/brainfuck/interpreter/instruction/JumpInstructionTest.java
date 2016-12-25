package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.JumpInstruction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
// TODO : mettre à jour les tests sur les JUMP
/**
 * @author Alexandre Hiltcher,François Melkonian
 */
public class JumpInstructionTest {
	private Memory memory;
	private ExecutionReader reader;
	private JumpInstruction instruction;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() throws Exception {
//		Charset charset = Charset.forName("UTF-8");
//		filename = "filename.bf";
//		data = ">+[++]--[]";
//		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
//			writer.write(data, 0, data.length());
//		} catch (IOException x) {
//
//			System.err.format("IOException: %s%n", x);
//		}
//		reader = new BfReader(filename);
//		memory = new Memory();
//		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new JumpInstruction(reader);
	}

	/**
	 * Jump.
	 * La case mémoire est à 0.
	 */
	@Test
	@Ignore
	public void jump() throws Exception {
		reader.seek();
		instruction.execute(memory);
		assertEquals(6, reader.getExecutionPointer());
	}

	/**
	 * Do not jump.
	 */
	@Test
	@Ignore
	public void doNotJump() throws Exception {
		memory.set(3);
		reader.seek();
		instruction.execute(memory);
		assertEquals(3, reader.getExecutionPointer());
	}

	/**
	 * Translate.
	 *
	 */
	@Test
	public void translate() throws Exception {
		JumpInstruction instruction = new JumpInstruction(null);
		assertEquals("ff7f00", instruction.translate() );
	}

}