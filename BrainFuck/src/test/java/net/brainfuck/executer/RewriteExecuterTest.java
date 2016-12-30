package net.brainfuck.executer;

import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.moveinstruction.LeftInstruction;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Check if rewrite instruction works and return it translation
 * @author Francois Melkonian
 */
public class RewriteExecuterTest {
	private List<Language> liste;
	private String outContent;

	/**
	 * We change Output to save all data what have been written
	 * when rewrite() is apply to instruction
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		outContent = "";
		Language.setInstructions(null,null);
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				outContent += String.valueOf((char)b);
			}
		}));
		liste = new ArrayList<>();
	}

	/**
	 * Check if all instructions can be translate to short instruction
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void execute() throws Exception {
		assertNotEquals(0,Language.values().length);
		for (Language g : Language.values()){
			AbstractInstruction i = g.getInterpreter();
			i.rewrite();
			assertTrue(outContent.endsWith(g.getShortSyntax()));
		}

		System.setOut(System.out);
	}

	/**
	 * Check if one instruction works
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void left() throws Exception {
		AbstractInstruction c = new LeftInstruction();
		c.rewrite();
		assertEquals("<",outContent);

	}

}