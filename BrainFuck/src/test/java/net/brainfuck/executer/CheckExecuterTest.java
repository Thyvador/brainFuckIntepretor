package net.brainfuck.executer;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.ArgumentExecuter;
import net.brainfuck.exception.IncorrectArgumentException;
import net.brainfuck.interpreter.Interpreter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author François Melkonian
 * @date 16/11/2016
 */
public class CheckExecuterTest {
	ArgumentAnalyzer argumentAnalyzer;

	@Before
	public void setUp() throws IncorrectArgumentException {
		Context.contextMap.get(Context.UNCHECK.getSyntax());
		String[] args = {"-p", "filename", "--check"};
		argumentAnalyzer = new ArgumentAnalyzer(args);
	}

	@Test
	public void execute() throws Exception {
		assertTrue(argumentAnalyzer.getFlags().contains(Context.CHECK.getSyntax()));
	}

	@Test
	public void initCpt() throws Exception {
		assertEquals(0, new CheckExecuter().getCpt());
	}


}