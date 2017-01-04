package net.brainfuck.interpreter.processing;

import net.brainfuck.common.Pair;
import net.brainfuck.common.StringParser;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.ContextExecuter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by davidLANG on 21/12/2016.
 */
public class ProcedureFunctionParser {
    List<ContextExecuter> contextExecuters;
    Map<String, Macro> macros;


    public ProcedureFunctionParser(List<ContextExecuter> contextExecuters, Map<String, Macro> macros) {
        this.contextExecuters = contextExecuters;
        this.macros = macros;
    }

    public String parseName(String definition) throws SyntaxErrorException {
        this.checkSyntax(definition);
        String name = definition.split("\\s")[1];
        name = name.substring(0, name.indexOf('('));

        return name;
    }

    private void checkArgumentsSyntaxe(String[] arguments) throws SyntaxErrorException {
        for (String argument : arguments) {
            if (!argument.matches("^[\\w\\d]+"))
                throw new SyntaxErrorException("Bad argument in procedure : " + argument);
        }
    }

    public List<String> parseArgument(String definition) throws SyntaxErrorException {

        String[] arguments = StringParser.getArguments(definition);
        if (arguments == null)
            return new ArrayList<>();
        this.checkArgumentsSyntaxe(arguments);
        return Arrays.asList(arguments);
    }


    private void checkSyntax(String definition) throws SyntaxErrorException {
        if (!definition.matches("^![\\w\\d]+\\s+[\\w\\d]+\\(.*\\)\\s*")) {
            throw new SyntaxErrorException("Bad definition of procedure");
        }
    }

    public Pair<List<AbstractInstruction>, JumpTable> parse(List<String> instructions) throws IOException, SyntaxErrorException, java.io.IOException, BracketsParseException {
        BfCompiler bfCompiler = new BfCompiler(contextExecuters, macros);

        return bfCompiler.compile(contextExecuters, instructions);
    }

}
