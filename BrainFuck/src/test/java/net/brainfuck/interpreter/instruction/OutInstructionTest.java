package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.Exception;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher
 */
public class OutInstructionTest {
	private OutputStreamWriter outputStream = new OutputStreamWriter(System.out);
	private Memory memory;
	private OutInstruction instruction;
	private String filename;

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {

		List<Language> langage = Arrays.asList(Language.LEFT,Language.LEFT);

		instruction = new OutInstruction(outputStream);
		ExecutionReader reader = new ExecutionReader(langage);
	}

	/**
	 * Out.
	 *

	 @Test
	public void out() throws Exception {
		memory.set('a');
		instruction.execute(memory,reader);
		assertEquals("a", outputStream.toString());
	}
	 */


	/**
	 * Translate.
	 *
	 */
	@Test
	public void translate() throws Exception{
	assertEquals("00ff00",instruction.translate());
	}

}