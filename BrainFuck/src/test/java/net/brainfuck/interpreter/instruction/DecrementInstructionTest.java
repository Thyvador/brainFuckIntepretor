package net.brainfuck.interpreter.instruction;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.instruction.operationinstruction.DecrementInstruction;

/**
 * @author Alexandre Hiltcher
 */
public class DecrementInstructionTest {
	private Memory memory;
	private DecrementInstruction instruction;


	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() throws Exception {
		memory = new Memory();
		instruction = new DecrementInstruction();
	}

	/**
	 * Decr.
	 */
	@Test
	public void decr() throws Exception {
		memory.set(50);
		instruction.execute(memory);
		assertEquals(49, memory.get());
	}

	/**
	 * Over flow.
	 *
	 * @throws MemoryOverFlowException    the memory over flow exception
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
	@Test(expected = MemoryOverFlowException.class)
	public void OverFlow() throws MemoryOverFlowException, MemoryOutOfBoundsException {
		instruction.execute(memory);
	}
	/**
	 * Translate.
	 */
	@Test
	public void translate() throws Exception {
		assertEquals("4b0082", instruction.translate());
	}
	
	@Test
	public void testGenerate() throws Exception {
		assertEquals("(*ptr)--;",instruction.generate() );
	}
}