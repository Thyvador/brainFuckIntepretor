package net.brainfuck.common;

import net.brainfuck.Main;
import net.brainfuck.executer.Context;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Francois Melkonian
 * @date 16/11/2016
 */
public class ArgumentAnalyzerTest {
	@Test
	public void getArgs() throws Exception {
		String[] args = {"-p","filename","-i","input","-o","pasca"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
		assertEquals(argumentAnalyzer.getArgument(ArgumentConstante.PATH),"filename");
		assertEquals(argumentAnalyzer.getArgument(ArgumentConstante.IN_PATH),"input");
		assertEquals(argumentAnalyzer.getArgument(ArgumentConstante.OUT_PATH),"pasca");
	}

	@Test
	public void getFlags() throws Exception {
		String[] args = {"-p","filename","-i","input","-o","pasca","--translate","--rewrite","--check"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
		assertTrue(argumentAnalyzer.getFlags().contains(Context.TRANSLATE.getSyntax()));
		assertTrue(argumentAnalyzer.getFlags().contains(Context.REWRITE.getSyntax()));
		assertTrue(argumentAnalyzer.getFlags().contains(Context.CHECK.getSyntax()));
		assertFalse(argumentAnalyzer.getFlags().contains(Context.UNCHECK.getSyntax()));
		assertFalse(argumentAnalyzer.getFlags().contains(Context.TRACE.getSyntax()));
	}

	@Test
	public void testArgument2() throws Exception {
		String[] args = {"-p","filename","-i","-o"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
	}

	@Test
	public void testArgument() throws Exception {
		String[] args = {"-p", "filename", "-i", "--translate"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
		argumentAnalyzer.getArgument(ArgumentConstante.IN_PATH);
	}

	@Test(expected = net.brainfuck.exception.IncorrectArgumentException.class)
	public void badArguments() throws Exception {
		String[] args = {"-pio","filename","pasca","--rewrite","--check"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
	}


}