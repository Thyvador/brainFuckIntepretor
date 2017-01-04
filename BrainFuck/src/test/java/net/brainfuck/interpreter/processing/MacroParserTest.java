package net.brainfuck.interpreter.processing;

import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.moveinstruction.RightInstruction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test macros
 * @author Francois Melkonian,davidLANG
 */
public class MacroParserTest {
    private MacroParser macroParser;

    @Before
    public void setUp() throws Exception {
        Language.setInstructions(null, null);
        macroParser = new MacroParser();
    }
    @Test
    public void saveMacroArg() throws Exception{
        macroParser.saveMacro("!test(arg) + arg");
        assertTrue(macroParser.getMacros().containsKey("test"));
    }

	@Test(expected = SyntaxErrorException.class)
	public void saveBadMacros() throws Exception{
		macroParser.saveMacro("!test(arg) + arg2");
	}

	@Test
	public void saveLongMacro() throws Exception{
		macroParser.saveMacro("!test LEFT");
		assertTrue(macroParser.getMacros().containsKey("test"));
	}
	@Test
	public void noParameter() throws Exception{
		macroParser.saveMacro("!test() LEFT");
		assertTrue(macroParser.getMacros().containsKey("test"));
	}

    @Test
    public void saveDoubleMacro() throws Exception{
        macroParser.saveMacro("!test LEFT");
        macroParser.saveMacro("!test RIGHT");
        assertEquals(1,macroParser.getMacros().size());
    }

    /**
     * Test macro without argument
     */
    @Test(expected = SyntaxErrorException.class)
    public void saveMacro() throws Exception{
	    macroParser.saveMacro("!  +");
    }

	@Test(expected = SyntaxErrorException.class)
	public void spaceArg() throws Exception{
		macroParser.saveMacro("!da(arg ument)  + arg");
	}
}
