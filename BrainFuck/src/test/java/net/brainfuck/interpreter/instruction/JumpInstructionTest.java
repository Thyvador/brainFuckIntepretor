package net.brainfuck.interpreter.instruction;

import static org.junit.Assert.assertEquals;

import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.BackInstruction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.Exception;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.JumpInstruction;

import java.util.Arrays;
import java.util.List;
/**
 * @author Alexandre Hiltcher,François Melkonian
 */
public class JumpInstructionTest {
	private Memory memory;
	private ExecutionReader reader;
	private JumpInstruction instruction;

	/**
	 * Create a.
	 */
	@Before
	public void setUp() throws Exception {
		Language.setInstructions(null, null);
		List<AbstractInstruction> langage = Arrays.asList(Language.JUMP.getInterpreter(), Language.BACK.getInterpreter());
		JumpTable jumpTable = new JumpTable(false);
		jumpTable.addInstruction(langage.get(0), 1);
		jumpTable.addInstruction(langage.get(1), 0);
		reader = new ExecutionReader(langage, jumpTable);
		Language.setExecutable(reader);
		memory = new Memory();
		instruction = new JumpInstruction(reader);
	}

	/**
	 * Jump.
	 * La case mémoire est à 0.
	 */
	@Test
	public void jump() throws Exception {
		memory.set(0);
		reader.getNext().execute(new Memory());
		assertEquals(1, reader.getExecutionPointer());
	}

	/**
	 * Do not jump.
	 */
	@Test
	public void doNotJump() throws Exception {
		memory.set(3);
		reader.getNext().execute(memory);
		assertEquals(0, reader.getExecutionPointer());
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
	
	@Test
	public void testGenerate() throws Exception {
		assertEquals("while(*ptr) {",instruction.generate() );
	}

	@Test
	public void rewrite(){
		assertEquals("[", instruction.rewrite());
	}

}