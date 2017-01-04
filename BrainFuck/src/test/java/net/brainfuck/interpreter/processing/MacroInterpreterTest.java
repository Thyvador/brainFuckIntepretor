package net.brainfuck.interpreter.processing;

import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Francois Melkonian
 */
public class MacroInterpreterTest {
	private MacroParser macroParser;
	private MacroInterpreter inter;

	@Before
	public void setUp() throws Exception {

		Language.setInstructions(null, null);
		macroParser = new MacroParser();
		macroParser.saveMacro("!test(arg) + arg");
		inter = new MacroInterpreter(macroParser.getMacros());

	}

	@Test
	public void writeMacro() throws Exception {;
		List<Language> z = inter.writeMacro("test(8)");
		assertEquals(8,z.size());
	}
	@Test
	public void writeMacroMultiplicateur() throws Exception {;
		List<Language> z = inter.writeMacro("test(8) 7");
		assertEquals(8*7,z.size());
	}

	@Test(expected = SyntaxErrorException.class)
	public void writeBadMacro() throws Exception {
		inter.writeMacro("test()");
	}

	@Test(expected = SyntaxErrorException.class)
	public void writeBadMacroMultiplicateur() throws Exception {
		inter.writeMacro("test() 2");
	}

	@Test(expected = SyntaxErrorException.class)
	public void writeNoParenthesisMultiplicateur() throws Exception {
		inter.writeMacro("test 2");
	}

	@Test(expected = SyntaxErrorException.class)
	public void writeWithALotArgMultiplicateur() throws Exception {
		 inter.writeMacro("test(2,3) 2");
	}

	@Test(expected = SyntaxErrorException.class)
	public void writeNoParenthesis() throws Exception {
		inter.writeMacro("test");
	}

	@Test(expected = SyntaxErrorException.class)
	public void writeWithALotArg() throws Exception {
		inter.writeMacro("test(2,3)");
	}

	@Test
	public void contains() throws Exception {
		assertTrue(inter.contains("test"));
		assertFalse(inter.contains("ted"));
	}

}