package net.brainfuck.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class TestMemory {
	Memory memory;

	@Before
	public void setUp() throws Exception {
		memory = new Memory();
	}

	@Test
	public void right() throws Exception {
		memory.right();
		assertEquals(1, memory.getIndex());
	}

	@Test
	public void left() throws Exception {
		memory.right();
		memory.right();
		memory.left();
		assertEquals(1, memory.getIndex());
	}

	@Test
	public void incr() throws Exception {
		memory.incr();
		assertEquals(1, memory.get());
	}

	@Test
	public void decr() throws Exception {
		memory.incr();
		memory.incr();
		memory.decr();
		assertEquals(1, memory.get());
	}

	@Test
	public void getIndex() throws Exception {
		assertEquals(0, memory.getIndex());
	}

}