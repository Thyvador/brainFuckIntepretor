package net.brainfuck.executer;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.exception.IncorrectArgumentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckExecuterTest tests the <code>CheckExecuter</code> class.
 *
 * @author François Melkonian
 * @date 16/11/2016
 */
public class CheckExecuterTest {
	private ArgumentAnalyzer argumentAnalyzer;

	/**
	 * Sets the arguments for the following tests.
	 *
	 * @throws IncorrectArgumentException
	 *             the incorrect argument exception
	 */
	@Before
	public void setUp() throws IncorrectArgumentException {
		Context.contextMap.get(Context.UNCHECK.getSyntax());
		String[] args = {"-p", "filename", "--check"};
		argumentAnalyzer = new ArgumentAnalyzer(args);
	}

	/**
	 * Tests the execute function.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void execute() throws Exception {
		assertTrue(argumentAnalyzer.getFlags().contains(Context.CHECK.getSyntax()));
	}

	/**
	 * Initialize the cpt.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void initCpt() throws Exception {
		assertEquals(0, new CheckExecuter().getCpt());
	}


}