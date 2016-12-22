package net.brainfuck.interpreter.processing;

import net.brainfuck.common.Logger;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.io.BfReader;
import net.brainfuck.io.Reader;

import java.util.Map;

/**
 * @author davidLANG
 */
public class BfPrecompiler {

    private Logger logger;
    private MacroParser macroParser = new MacroParser();
    private String lastInstruction;
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
        boolean endofCompile = false;
        String instruction;

        while (!endofCompile && (instruction = ((BfReader) reader).getNextMacro()) != null) {
            if (Language.languageMap.get(instruction) == null && instruction.charAt(0) == BfReader.PREPROCESSING) {
                macroParser.saveMacro(instruction);
            } else {
                this.lastInstruction = instruction;
                endofCompile = true;
            }
            logger.incrInstruction();
        }
        return macroParser.getMacros();
    }

    public String getLastInstruction() {
        return lastInstruction;
    }
}
