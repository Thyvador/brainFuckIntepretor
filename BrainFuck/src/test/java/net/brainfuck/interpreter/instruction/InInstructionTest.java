package net.brainfuck.interpreter.instruction;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.interpreter.instruction.intoutinsruction.InInstruction;

/**
 * @author Alexandre Hiltcher,François Melkonian
 */
public class InInstructionTest {
	private Memory memory;
	private InInstruction instruction;

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {



		memory = new Memory();
		InputStreamReader inStream = new InputStreamReader(new InputStream() {
			int i = 0;
			@Override
			public int read() throws IOException {
				if(++i < 100) return 5 + i;
				return '\0';
			}
		});
		instruction = new InInstruction(inStream);

	}

	/**
	 * In.
	 */
	@Test
	public void in() throws Exception {
		instruction.execute(memory);
		assertEquals(6, memory.get());
	}

	/**
	 * End of In.
	 *
	 */
	@Test(expected = FileNotFoundIn.class)
	public void endIn() throws Exception {
		InputStreamReader inStream = new InputStreamReader(new InputStream() {
			@Override
			public int read() throws IOException {
				return -1;
			}
		});
		instruction = new InInstruction(inStream);
		instruction.execute(memory);
	}


	/**
	 * Translate.
	 *
	 */
	@Test
	public void translate() throws Exception {
		InInstruction instruction = new InInstruction(null);
		assertEquals("ffff00", instruction.translate() );
	}

	@Test
	public void testGenerate() throws Exception {
		assertEquals("(*ptr) = getchar();",instruction.generate() );
	}

}