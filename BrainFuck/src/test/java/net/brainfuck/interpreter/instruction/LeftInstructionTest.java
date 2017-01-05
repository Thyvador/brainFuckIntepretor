package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.moveinstruction.LeftInstruction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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