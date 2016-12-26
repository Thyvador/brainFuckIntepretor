package net.brainfuck.interpreter.processing.procedure;

import net.brainfuck.common.Pair;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.ContextExecuter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.processing.BfCompiler;
import net.brainfuck.interpreter.processing.Macro;

import java.util.List;
import java.util.Map;

/**
 * Created by davidLANG on 21/12/2016.
 */
public class ProcedureParser {
    List<ContextExecuter> contextExecuters;
    Map<String, Macro> macros;


    public ProcedureParser(List<ContextExecuter> contextExecuters, Map<String, Macro> macros) {
        this.contextExecuters = contextExecuters;
        this.macros = macros;
    }

    public String parseName(String definition) throws SyntaxErrorException {
        this.checkSyntax(definition);
        String name = definition.split("\\s")[1];
        name = name.substring(0, name.indexOf('('));

        return name;
    }

    private void parseArgument() {
        //TODO pour les procédures avec arguments
    }


    private void checkSyntax(String definition) throws SyntaxErrorException {
        if ( !definition.matches("^!procedure\\s+[\\w\\d]+\\(.*\\)\\s*")) {
            throw new SyntaxErrorException("Bad definition of procedure");
        }
    }

    public Pair<List<Language>, JumpTable> parse(List<String> instructions) throws IOException, SyntaxErrorException, java.io.IOException, BracketsParseException {
        Pair<List<Language>, JumpTable> compiledProcedure;
        BfCompiler bfCompiler = new BfCompiler(contextExecuters, macros);

        return compiledProcedure = bfCompiler.compile(contextExecuters, instructions);
    }

}
