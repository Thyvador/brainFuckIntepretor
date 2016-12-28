package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.intoutinsruction.InInstruction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher,François Melkonian
 */
public class InInstructionTest {
	private Memory memory;
	private String filename;
	private ExecutionReader reader;
	private InInstruction instruction;

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {


		List<AbstractInstruction> langage = Arrays.asList(Language.RIGHT.getInterpreter(),Language.RIGHT.getInterpreter());

		reader = new ExecutionReader(langage, null);
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


}