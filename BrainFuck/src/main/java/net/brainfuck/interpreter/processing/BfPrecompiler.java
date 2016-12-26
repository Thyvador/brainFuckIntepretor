package net.brainfuck.interpreter.processing;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Pair;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.ContextExecuter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.processing.procedure.ProcedureParser;
import net.brainfuck.io.BfReader;
import net.brainfuck.io.Reader;

import java.util.List;
import java.util.Map;

/**
 * @author davidLANG
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
            if (Language.languageMap.get(instruction) == null && instruction.charAt(0) == BfReader.PREPROCESSING) {
                macroParser.saveMacro(instruction);
                logger.incrInstruction();
            }
        }
        return macroParser.getMacros();
    }

    public void analyzeProcedure(List<ContextExecuter> contextExecuters, Map<String, Macro> macros) throws IOException, SyntaxErrorException, java.io.IOException, BracketsParseException {
        List<String> instructions;
        String definition;
        Pair<List<Language>, JumpTable> procedure;
        String procedureName;
        ProcedureParser procedureParser = new ProcedureParser(contextExecuters, macros);

        while ((instructions = ((BfReader) reader).getNextProcedure()) != null)  {
            definition = instructions.remove(0);

            // TODO ALEX
            // ICI ICI ICI
            procedureName = procedureParser.parseName(definition);
            procedure = procedureParser.parse(instructions, definition);
            System.out.println("toto");
        }
    }

}
