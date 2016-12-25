package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher,François Melkonian
 */
public class IncrementInstructionTest {
	private Memory memory;
	private IncrementInstruction instruction;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		memory = new Memory();
		instruction = new IncrementInstruction();
	}

	/**
	 * Incr.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void incr() throws Exception {
		instruction.execute(memory);
		assertEquals(1, memory.get());
	}

	/**
	 * Over flow.
	 *
	 * @throws MemoryOverFlowException
	 *             the memory over flow exception
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	@Test(expected = MemoryOverFlowException.class)
	public void OverFlow() throws MemoryOverFlowException, MemoryOutOfBoundsException {
		memory.set(255);
		instruction.execute(memory);
	}


	/**
	 * Translate.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void translate() throws Exception {
		instruction = new IncrementInstruction();
		assertEquals("ffffff",instruction.translate());
	}

}