package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.moveinstruction.RightInstruction;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher, Francois Melkonian
 */
public class RightInstructionTest {

	Memory memory;
	RightInstruction instruction;

	/**
	 * Setup memory and the instruction to test
	 *
	 */
	@Before
	public void setUp() throws Exception {
		memory = new Memory();
		instruction = new RightInstruction();
	}

	/**
	 * Right.
	 */
	@Test
	public void right() throws Exception {
		instruction.execute(memory);
		assertEquals(1, memory.getIndex());
	}

	/**
	 * Out of bound right.
	 *
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	@Test(expected = MemoryOutOfBoundsException.class)
	public void OutOfBoundRight() throws Exception {
		for (int i = 0; i < 30001; i++) {
			instruction.execute(memory);
		}
	}

	/**
	 * Translate.
	 *
	 */
	@Test
	public void translate() throws Exception {
		assertEquals("0000ff",instruction.translate() );
	}

}