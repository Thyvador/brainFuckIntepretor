package net.brainfuck.common;

import net.brainfuck.exception.IncorrectArgumentException;
import net.brainfuck.executer.Context;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The Class ArgumentAnalyzerTest test the <code>ArgumentAnalyser</code> class.
 *
 * @author Francois Melkonian
 */
public class ArgumentAnalyzerTest {

	/**
	 * Check if the arguments returned by the function getArguments
	 * are the on passed to the program.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getArgs() throws Exception {
		String[] args = {"-p", "filename", "-i", "input", "-o", "pasca"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
		assertEquals(argumentAnalyzer.getArgument(ArgumentConstante.PATH), "filename");
		assertEquals(argumentAnalyzer.getArgument(ArgumentConstante.IN_PATH), "input");
		assertEquals(argumentAnalyzer.getArgument(ArgumentConstante.OUT_PATH), "pasca");
	}

	/**
	 * Check if the flags returned by the function getArguments
	 * are the on passed to the program.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getFlags() throws Exception {
		String[] args = {"-p", "filename", "-i", "input", "-o", "pasca", "--translate", "--rewrite", "--check"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
		assertTrue(argumentAnalyzer.getFlags().contains(Context.TRANSLATE.getSyntax()));
		assertTrue(argumentAnalyzer.getFlags().contains(Context.REWRITE.getSyntax()));
		assertTrue(argumentAnalyzer.getFlags().contains(Context.CHECK.getSyntax()));
		assertFalse(argumentAnalyzer.getFlags().contains(Context.UNCHECK.getSyntax()));
		assertFalse(argumentAnalyzer.getFlags().contains(Context.TRACE.getSyntax()));
	}

	/**
	 * Test if an exception is thrown when a bad argument is put through the program.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testArgument2() throws IncorrectArgumentException {
		String[] args = {"-p", "filename", "-i", "-o"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
	}

	/**
	 * Test if an exception is thrown when a bad argument is put through the program.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testArgument() throws IncorrectArgumentException {
		String[] args = {"-p", "filename", "-i", "--translate"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
		argumentAnalyzer.getArgument(ArgumentConstante.IN_PATH);
	}

	/**
	 * Test if an exception is thrown when a bad argument is put through the program.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = net.brainfuck.exception.IncorrectArgumentException.class)
	public void badArguments() throws Exception {
		String[] args = {"-pio", "filename", "pasca", "--rewrite", "--check"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
	}


}