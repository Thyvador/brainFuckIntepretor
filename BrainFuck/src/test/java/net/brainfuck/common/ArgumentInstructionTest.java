package net.brainfuck.common;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.interpreter.JumpTable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * The Class ArgumentInstructionTest tests the <code>ArgumentInstruction</code> class.
 */
public class ArgumentInstructionTest {

	private Memory memory;
	private Reader reader;
	private JumpTable jumpTable;

	private ArgumentInstruction toTest;

	/**
	 * Sets up the memory and the reader for the following tests.
	 */
	@Before
	public void setUp() {
		memory = new Memory();
		try {
			reader = new BfReader("Brainfuck/src/test/resources/assets/brainfucktest/common/yapi.bf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		toTest = new ArgumentInstruction(memory, reader, jumpTable);
	}

	/**
	 * Test getter.
	 */
	@Test
	public void testGetter() {
		assertSame(toTest.getMemory(), memory);
		assertSame(toTest.getReader(), reader);
		assertSame(toTest.getJumpTable(), jumpTable);
	}

}
