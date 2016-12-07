package net.brainfuck.interpreter.compiler;

import net.brainfuck.common.StringParser;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author davidLANG
 */
class MacroInterpreter {
    private Map<String, Macro> macros = new HashMap<>();

    MacroInterpreter(Map<String, Macro> macros) {
        this.macros = macros;
    }


    private String getMacroName(String definition) {
        return definition.split("\\(")[0];
    }

    private List<Integer> getMacroArgument(String[] arguments) throws SyntaxErrorException {
        List<Integer> values = new ArrayList<>();

        if (arguments != null) {
            for (String value : arguments) {
                value = value.trim();
                if (!StringParser.isNumeric(value)) {
                    throw new SyntaxErrorException("illegal macro argument " + value);
                }
                values.add(Integer.parseUnsignedInt(value));
            }
        }
        return values;
    }

    private int getMultiplicator(String str) throws SyntaxErrorException {
        String multiplicateur = str.trim();
        if (!StringParser.isNumeric(multiplicateur)) {
            throw new SyntaxErrorException("bad multiplcator " + multiplicateur);
        }
        return Integer.parseUnsignedInt(multiplicateur);
    }

    /**
     * Write macro.
     *
     * @param instruction the instruction
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws BracketsParseException the brackets parse exception
     */
    List<Language> writeMacro(String instruction) throws IOException, BracketsParseException, SyntaxErrorException {
        if (!StringParser.isCorrectParentheses(instruction)) {
            throw new SyntaxErrorException("Bad parenthesis " + instruction);
        }
        List<Language> programme = new ArrayList<>();
        String[] definitions;
        if (StringParser.containsParenthesis(instruction)) {
            definitions = instruction.split("\\)");
        } else {
            if (!StringParser.containsSpace(instruction)) {
                throw new SyntaxErrorException("no instruction for macro " + instruction);
            }
            definitions = StringParser.splitSpace(instruction);
        }

        String[] arguments = StringParser.containsParenthesis(instruction) ?
                StringParser.getArguments(instruction) : null;
        String macroName = this.getMacroName(definitions[0]);

        if (macros.containsKey(macroName)) {
            int length = definitions.length;
            if (length > 2) {
                throw new SyntaxErrorException("Syntax behind macro '" + definitions[0] + "'");
            }

            int number = length == 2 ? getMultiplicator(definitions[1]) : 1;
            List<Integer> values = this.getMacroArgument(arguments);
            Macro macro = macros.get(macroName);
            for (int i = 0; i < number; i++) {
                programme.addAll(macro.write(values));
            }
        }
        return programme;
    }

    public boolean contains(String key) {
        return macros.containsKey(key);
    }


}
