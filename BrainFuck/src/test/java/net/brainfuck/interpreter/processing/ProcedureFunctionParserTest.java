package net.brainfuck.interpreter.processing;

import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.ContextExecuter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author davidLANG
 */
public class ProcedureFunctionParserTest {
    private ProcedureFunctionParser procedureFunctionParser;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        List<ContextExecuter> contextExecuters = new ArrayList<>();
        contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        this.procedureFunctionParser = new ProcedureFunctionParser(contextExecuters, new HashMap<>());
    }

    @Test
    public void parseName() throws SyntaxErrorException {
        String res = procedureFunctionParser.parseName("!function toto(arg1, arg2)");
        Assert.assertEquals(res, "toto");
    }

    @Test(expected=SyntaxErrorException.class)
    public void parseNameError() throws SyntaxErrorException {
        procedureFunctionParser.parseName("!function toto(arg1, arg2");
    }

    @Test(expected=SyntaxErrorException.class)
    public void parseNameError2() throws SyntaxErrorException {
        procedureFunctionParser.parseName("!function totoarg1, arg2");
    }

    @Test(expected=SyntaxErrorException.class)
    public void parseNameError3() throws SyntaxErrorException {
        procedureFunctionParser.parseName("!function toto (arg1, arg2)");
    }

    @Test(expected=SyntaxErrorException.class)
    public void parseNameError4() throws SyntaxErrorException {
        procedureFunctionParser.parseName("!procedure toto(arg1, arg2) ++");
    }

    @Test
    public void parseArgument() throws SyntaxErrorException {
        List<String> res0 = procedureFunctionParser.parseArgument("!procedure toto()");
        List<String> check = new ArrayList<>();
        Assert.assertEquals(res0, check);

        List<String> res1 = procedureFunctionParser.parseArgument("!procedure toto(arg1)");
        check.add("arg1");
        Assert.assertEquals(res1, check);


        List<String> res2 = procedureFunctionParser.parseArgument("!procedure toto(arg1, arg2)");
        check.add("arg2");
        Assert.assertEquals(res2, check);
    }

    @Test(expected = SyntaxErrorException.class)
    public void parseArgumentError() throws SyntaxErrorException {
        List<String> res0 = procedureFunctionParser.parseArgument("!procedure toto(titi toto)");
    }

    @Test(expected = SyntaxErrorException.class)
    public void parseArgumentError1() throws SyntaxErrorException {
        List<String> res0 = procedureFunctionParser.parseArgument("!procedure toto(titi^toto)");
    }

    @Test(expected = SyntaxErrorException.class)
    public void parseArgumentError2() throws SyntaxErrorException {
        List<String> res0 = procedureFunctionParser.parseArgument("!procedure toto(titi/toto)");
    }

    @Test(expected = SyntaxErrorException.class)
    public void parseArgumentError3() throws SyntaxErrorException {
        List<String> res0 = procedureFunctionParser.parseArgument("!procedure toto(titi;toto)");
    }

    @Test(expected = SyntaxErrorException.class)
    public void parseArgumentError4() throws SyntaxErrorException {
        List<String> res0 = procedureFunctionParser.parseArgument("!procedure toto(titi())");
    }
}
