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
 * @author davidLANG
 */
public class ProcedureFunctionParser {
    List<ContextExecuter> contextExecuters;
    Map<String, Macro> macros;


    /**
     * Constructor
     *
     * @param contextExecuters the list of contextExecuters
     * @param macros the list of macros
     */
    public ProcedureFunctionParser(List<ContextExecuter> contextExecuters, Map<String, Macro> macros) {
        this.contextExecuters = contextExecuters;
        this.macros = macros;
    }

    /**
     * Extract the name of the definition of a procedure or a function
     *
     * @param definition the definition of the procedure "!procedure name()"
     * @return the extracted name of the definition
     * @throws SyntaxErrorException {@link SyntaxErrorException SyntaxErrorException} if the name of function is not formated correctly
     */
    public String parseName(String definition) throws SyntaxErrorException {
        this.checkSyntax(definition);
        String name = definition.split("\\s")[1];
        name = name.substring(0, name.indexOf('('));

        return name;
    }

    /**
     * Check the syntax of the string array is correct
     *
     * @param arguments the string array arguments
     * @throws SyntaxErrorException if arguments is not corretly syntaxed
     */
    private void checkArgumentsSyntaxe(String[] arguments) throws SyntaxErrorException {
        for (String argument : arguments) {
            if (!argument.matches("^[\\w\\d]+"))
                throw new SyntaxErrorException("Bad argument in procedure : " + argument);
        }
    }

    /**
     * Extrated the arguments of the definition of a procedure or a function.
     * ex : :procedure test(arg, arg2)  =>  {"arg", "arg2"}
     *
     * @param definition the definition of a procedure
     * @return the argument extraxted of a procedure or a function
     * @throws SyntaxErrorException if the arguments or not correctly syntaxed
     */
    public List<String> parseArgument(String definition) throws SyntaxErrorException {

        String[] arguments = StringParser.getArguments(definition);
        if (arguments == null)
            return new ArrayList<>();
        this.checkArgumentsSyntaxe(arguments);
        return Arrays.asList(arguments);
    }

    /**
     * Check if the syntax of a definition of a function or a procedure is correct
     *
     * @param definition the definition of a function or a procedure
     * @throws SyntaxErrorException if the syntax is not correct
     */
    private void checkSyntax(String definition) throws SyntaxErrorException {
        if (!definition.matches("^![\\w\\d]+\\s+[\\w\\d]+\\(.*\\)\\s*")) {
            throw new SyntaxErrorException("Bad definition of procedure");
        }
    }

    /**
     * Compile the instruction of a procedure or a function
     *
     * @param instructions the instructions of the procedure / function
     * @return
     * @throws IOException {@link IOException IOException}
     * @throws SyntaxErrorException {@link SyntaxErrorException SyntaxErrorException}
     * @throws BracketsParseException {@link BracketsParseException BracketsParseException}
     */
    public Pair<List<AbstractInstruction>, JumpTable> parse(List<String> instructions) throws IOException, SyntaxErrorException, BracketsParseException, java.io.IOException {
        BfCompiler bfCompiler = new BfCompiler(contextExecuters, macros);

        return bfCompiler.compile(contextExecuters, instructions);
    }

}
