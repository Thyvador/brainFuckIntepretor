package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.Exception;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.intoutinsruction.OutInstruction;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher, Francois Melkonian
 */
public class OutInstructionTest {
	private Memory memory;
	private OutInstruction instruction;
	static List<Integer>out = new ArrayList();

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {
		instruction = new OutInstruction(new OutputStreamWriter(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				out.add(b);
			}
		}));
		memory = new Memory();
	}

	/**
	 * Out.
	 *
	 */

	 @Test
	public void out() throws Exception {
		memory.set('a');
		instruction.execute(memory);
		assertEquals('a',(int)out.get(0));
	}


	/**
	 * Translate.
	 *
	 */
	@Test
	public void translate() throws Exception{
	assertEquals("00ff00",instruction.translate());
	}
	
	@Test
	public void testGenerate() throws Exception {
		assertEquals("putchar(*ptr);",instruction.generate() );
	}

}