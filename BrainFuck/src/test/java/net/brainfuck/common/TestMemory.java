package net.brainfuck.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO: Auto-generated Javadoc
/**
 * Teh TestMemory tests the <code>Memory</code> class.
 *
 * @author Alexandre HILTCHER
 */
public class TestMemory {
	Memory memory;

	/**
	 * Sets the memory for the following tests.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		memory = new Memory();
	}

	/**
	 * Tests the right method.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void right() throws Exception {
		memory.right();
		assertEquals(1, memory.getIndex());
	}

	/**
	 * Tests the left method .
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void left() throws Exception {
		memory.right();
		memory.right();
		memory.left();
		assertEquals(1, memory.getIndex());
	}

	/**
	 * Tests the incr method.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void incr() throws Exception {
		memory.incr();
		assertEquals(1, memory.get());
	}

	/**
	 * test the decr method.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void decr() throws Exception {
		memory.incr();
		memory.incr();
		memory.decr();
		assertEquals(1, memory.get());
	}

	/**
	 * Test if the reurned index is the current index.
	 *
	 * @return the index
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getIndex() throws Exception {
		assertEquals(0, memory.getIndex());
	}

}