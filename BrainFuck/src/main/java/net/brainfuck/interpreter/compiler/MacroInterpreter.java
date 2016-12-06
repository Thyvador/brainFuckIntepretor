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

        if (macros.containsKey(definitions[0])) {
            int length = definitions.length;
            if (length > 2) {
                throw new SyntaxErrorException("Syntax behind macro '" + definitions[0] + "'");
            }

            int number = (length == 2 && RegexParser.isNumeric(definitions[1])) ? Integer.parseUnsignedInt(definitions[1]) : 1;
            Macro macro = macros.get(definitions[0]);
            List<Integer> arguments = new ArrayList<>();
            arguments.add(1);
            for (int i = 0; i < number; i++) {
                programme.addAll(macro.write(arguments));
            }
        }
        return programme;
    }

    public boolean contains(String key) {
        return macros.containsKey(key);
    }


}
