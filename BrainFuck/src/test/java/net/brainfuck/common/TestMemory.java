package net.brainfuck.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SegmentationFaultException;

/**
 * Teh TestMemory tests the <code>Memory</code> class.
 *
 * @author Alexandre HILTCHER
 */
public class TestMemory {
	private Memory memory;

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
	
	@Test(expected=MemoryOutOfBoundsException.class)
	public void testRightOutOfBounds() throws Exception {
		for (int i = 0; i < 30000; i++) {
			memory.right();
		}
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
	
	@Test(expected=MemoryOutOfBoundsException.class)
	public void testLeftOutOfBounds() throws Exception {
		memory.left();
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
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getIndex() throws Exception {
		assertEquals(0, memory.getIndex());
	}
	
	@Test
	public void testSet() throws Exception {
		memory.set(3);
		assertSame((short)3, memory.get());
	}
	
	@Test(expected=MemoryOverFlowException.class)
	public void testSetOverflow1() throws Exception {
		memory.set(-1);
	}
	
	@Test(expected=MemoryOverFlowException.class)
	public void testSetOverflow2() throws Exception {
		memory.set(256);
	}
	
	@Test(expected=SegmentationFaultException.class)
	public void testLock() throws Exception {
		for (int i = 0; i < 3; i++) 
			memory.right();
		memory.lock();
		memory.left();
	}
	
	@Test
	public void testLock2() throws Exception {
		for (int i = 0; i < 3; i++) 
			memory.right();
		memory.lock();
		memory.right();
	}
	
	@Test(expected=MemoryOutOfBoundsException.class)
	public void testLockOutOfBounds() throws Exception {
		for (int i = 0; i < 30000; i++) 
			memory.right();
		memory.lock();
	}
	
	@Test
	public void testSetArguments() throws Exception {
		memory.right();
		for (int i=0; i<3; i++)
			memory.incr();
		memory.right();
		for (int i=0; i<4; i++)
			memory.incr();
		memory.lock().setArguments(1, 2);
		memory.left();
		assertSame(memory.get(), (short) 4);
		memory.left();
		assertSame(memory.get(), (short) 3);
	}
	
	@Test
	public void testSetArguments2() throws Exception {
		for (int i = 0; i < 3; i++) 
			memory.right();
		memory.lock().setArguments(1, 256);
		memory.left();
		assertSame(memory.get(), (short) 0);
		memory.left();
		assertSame(memory.get(), (short) 0);
	}
	
	@Test(expected=MemoryOutOfBoundsException.class)
	public void testSetArgumentsOverflow() throws Exception {
		for (int i = 0; i < 3; i++) 
			memory.right();
		memory.lock().setArguments(1, 30000);
	}
	
	@Test(expected=MemoryOutOfBoundsException.class)
	public void testSetArgumentsOverflow2() throws Exception {
		for (int i = 0; i < 3; i++) 
			memory.right();
		memory.lock().setArguments(1, -1);
	}
	
	@Test(expected=MemoryOutOfBoundsException.class)
	public void testSetArgumentsOutOfBounds() throws Exception {
		for (int i = 0; i < 30000-2; i++) 
			memory.right();
		memory.lock().setArguments(1, 2, 3);
	}

	@Test
	public void testUnlock() throws Exception {
		for (int i = 0; i < 3; i++) 
			memory.right();
		memory.lock();
		memory.unlock(false);
		memory.left();
		memory.left();
		memory.right();
		memory.right();
		memory.right();
		memory.right();
	}
	
	@Test
	public void testUnlock2() throws Exception {
		for (int i = 0; i < 3; i++) 
			memory.right();
		memory.lock();
		memory.unlock(true);
		memory.left();
		memory.left();
		memory.right();
		memory.right();
		memory.right();
		memory.right();
	}
	
}