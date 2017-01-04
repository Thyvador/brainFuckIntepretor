package net.brainfuck.interpreter.processing;

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
        if (StringParser.containsParenthesis(definition))
            return definition.substring(0, definition.indexOf("("));
        if (StringParser.containsSpace(definition))
            return definition.substring(0, definition.indexOf(" "));
        return definition;
    }

    private String[] getArgumentText(String instruction) {
        return StringParser.containsParenthesis(instruction) ?
                StringParser.getArguments(instruction) : null;
    }

    private List<Integer> getArgumentValue(String[] arguments) throws SyntaxErrorException {
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

        // Récupération de ce qui se trouve derrière la macro (ex: macro 2 => 2  ou macro(2,3) 3 => 3)
        String[] definitions = StringParser.containsParenthesis(str) ? str.split("\\)") : StringParser.splitSpace(str);
        int length = definitions.length;
        if (length > 2) throw new SyntaxErrorException("Bad definition of macro " + str);

        // Cas ou la ligne contient un multiplicateur de macro (ex: macroname 2 ou macroname(2,3) 3)
        if (length == 2) {
            String multiplicateur = definitions[1].trim();
            // Le multiplacteur n'est pas un entier positif
            if (!StringParser.isNumeric(multiplicateur)) {
                throw new SyntaxErrorException("bad multiplcator " + multiplicateur);
            }
            return Integer.parseUnsignedInt(multiplicateur);
        }

        // cas ou la ligne ne contient pas de multiplcateur
        return 1;
    }

    /**
     * Write macro.
     *
     * @param instruction the instruction
     * @return true, if successful
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws BracketsParseException the brackets parse exception
     */
    List<Language> writeMacro(String instruction) throws IOException, BracketsParseException, SyntaxErrorException {
        // Verification de parenthese : throw une erreur si c'est mal parenthese
        this.checkParentheses(instruction);

        // Récupération du multiplicateur, des valeurs des arguments, et de l'objet macro a écrire
        int number = getMultiplicator(instruction);
        List<Integer> values = this.getArgumentValue(this.getArgumentText(instruction));
        Macro macro = macros.get(this.getMacroName(instruction));

        // Récupération des instruction à executer
        List<Language> programme = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            programme.addAll(macro.write(values));
        }

        return programme;
    }

    private void checkParentheses(String instruction) throws SyntaxErrorException {
        if (!StringParser.isCorrectParentheses(instruction)) {
            throw new SyntaxErrorException("Bad parenthesis " + instruction);
        }
    }

    public boolean contains(String key) {
        return macros.containsKey(key);
    }


}
