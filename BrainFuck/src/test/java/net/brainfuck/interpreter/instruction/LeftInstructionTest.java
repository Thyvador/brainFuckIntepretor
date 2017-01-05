package net.brainfuck.interpreter.instruction;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.instruction.moveinstruction.LeftInstruction;

/**
 * @author Alexandre Hiltcher, Francois Melkonian
 */
public class LeftInstructionTest {
	private Memory memory;
	private LeftInstruction instruction;

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {
		memory = new Memory();
		instruction = new LeftInstruction();
	}

	/**
	 * Left.
	 *
	 */
	@Test
	public void left() throws Exception {
		memory.right();
		memory.right();
		instruction.execute(memory);
		assertEquals(1, memory.getIndex());
	}

	/**
	 * Out of bound left.
	 *
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	@Test(expected = MemoryOutOfBoundsException.class)
	public void OutOfBoundLeft() throws Exception {
		instruction.execute(memory);

	}

	/**
	 * Translate.
	 *
	 */
	@Test
	public void translate() throws Exception {
		instruction = new LeftInstruction();
		assertEquals("9400d3",instruction.translate() );
	}

	@Test
	public void testGenerate() throws Exception {
		assertEquals("ptr--;",instruction.generate() );
	}
	
}