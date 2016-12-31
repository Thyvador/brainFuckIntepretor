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

	/**
	 * We change Output to save all data what have been written
	 * when rewrite() is apply to instruction
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		Language.setInstructions(null,null);
	}

	/**
	 * Check if all instructions can be translate to short instruction
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		assertNotEquals(0,Language.values().length);
		for (Language g : Language.values()){
			AbstractInstruction i = g.getInterpreter();
			i.rewrite();
			assertEquals(g.getShortSyntax(),i.rewrite());
		}

	}

	/**
	 * Check if one instruction works
	 * @throws Exception
	 */
	@Test
	public void left() throws Exception {
		AbstractInstruction c = new LeftInstruction();
		assertEquals("<",c.rewrite());

	}

}