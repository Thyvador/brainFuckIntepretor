package net.brainfuck.interpreter.compiler;

import net.brainfuck.common.RegexParser;
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

    private List<Integer> getMacroArgument(String definition) throws SyntaxErrorException {
        List<Integer> values = new ArrayList<>();
        String strValues = definition.substring(definition.indexOf('(')+1,definition.indexOf(')'));

//        if (tmp.length > 1) {
//            throw new SyntaxErrorException("Bad macro utilisation " + definition);
//        }
        for (String value : strValues.split(",")) {
            if (!RegexParser.isNumeric(value)) {
                throw new SyntaxErrorException("illegal macro argument " + value);
            }
            values.add(Integer.parseUnsignedInt(value));
        }
        return values;
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
        String[] definitions = RegexParser.splitSpace(instruction);
        List<Language> programme = new ArrayList<>();
        String macroName = this.getMacroName(definitions[0]);

        if (macros.containsKey(macroName)) {
            int length = definitions.length;
            if (length > 2) {
                throw new SyntaxErrorException("Syntax behind macro '" + definitions[0] + "'");
            }

            int number = (length == 2 && RegexParser.isNumeric(definitions[1])) ? Integer.parseUnsignedInt(definitions[1]) : 1;
            List<Integer> values = this.getMacroArgument(definitions[0]);
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
