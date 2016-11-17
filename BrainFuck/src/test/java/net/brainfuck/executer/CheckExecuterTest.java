package net.brainfuck.executer;

import net.brainfuck.common.ArgumentAnalyzer;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author FranÃ§ois Melkonian
 * @date 16/11/2016
 */
public class CheckExecuterTest {
	@Test
	public void execute() throws Exception {
		Context.contextMap.get(Context.UNCHECK.getSyntax());
		String[] args = {"-p","filename","--check"};
		ArgumentAnalyzer argumentAnalyzer = new ArgumentAnalyzer(args);
		assertTrue(argumentAnalyzer.getFlags().contains(Context.CHECK.getSyntax()));
	}

	@Test
	public void getCpt() throws Exception {

	}

}