package net.brainfuck.interpreter.processing;

import java.util.List;
import java.util.Map;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Pair;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.Function;
import net.brainfuck.common.executable.Procedure;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.ContextExecuter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.io.BfReader;
import net.brainfuck.io.Reader;

/**
 * @author FooBar Team
 */
public class BfPrecompiler {

    private Logger logger;
    private MacroParser macroParser = new MacroParser();
    private Reader reader;

    public BfPrecompiler(Reader reader) {
        this.reader = reader;
        this.logger = Logger.getInstance();
    }


    /**
     * Analyze macro.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws BracketsParseException the brackets parse exception
     * @throws SyntaxErrorException the syntax error exception
     */
    public Map<String, Macro> analyzeMacro() throws java.io.IOException, IOException, BracketsParseException, SyntaxErrorException {
        String instruction;

        while ((instruction = ((BfReader) reader).getNextMacro()) != null) {
            if (Language.getInstructionMap().get(instruction) == null && instruction.charAt(0) == BfReader.PREPROCESSING) {
                macroParser.saveMacro(instruction);
                logger.incrInstruction();
            }
        }
        return macroParser.getMacros();
    }

    /**
     * Analyze a Procedure or Function and compile it and add it to the language
     *
     * @param contextExecuters List of the context of the program
     * @param macros List of macro of the program
     * @param memory memory of the program
     * @throws IOException if the reader throw an exception
     * @throws SyntaxErrorException if an error of syntax is encounter while the analyze
     * @throws java.io.IOException  if the reader throw an exception
     * @throws BracketsParseException if an error of bracket is encounter while the analyze
     * @throws MemoryOutOfBoundsException throw by memory
     */
    public void analyzeProcedure(List<ContextExecuter> contextExecuters, Map<String, Macro> macros, Memory memory) throws IOException, SyntaxErrorException, java.io.IOException, BracketsParseException, MemoryOutOfBoundsException {
        List<String> instructions;
        String definition;
        Pair<List<AbstractInstruction>, JumpTable> procedure;
        String procedureName;
        List<String> procedureArgument;
        ProcedureFunctionParser procedureParser = new ProcedureFunctionParser(contextExecuters, macros);

        while ((instructions = ((BfReader) reader).getNextProcedure()) != null)  {
            definition = instructions.remove(0);

            procedureName = procedureParser.parseName(definition);
            procedureArgument = procedureParser.parseArgument(definition);
            Executable executable;
            if (definition.matches("^!procedure.*"))
                executable = new Procedure(procedureName, memory, procedureArgument);
            else
                executable = new Function(procedureName,memory, procedureArgument);
            Language.addInstruction(executable, procedureName);
            procedure = procedureParser.parse(instructions);
            executable.addPair(procedure);

        }
    }

}
