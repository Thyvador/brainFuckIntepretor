package net.brainfuck.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO: Auto-generated Javadoc
/**
 * Created by Alexandre on 16/11/2016.
 */
public class TestMemory {
	Memory memory;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		memory = new Memory();
	}

	/**
	 * Right.
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
	 * Left.
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
	 * Incr.
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
	 * Decr.
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
	 * Gets the index.
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