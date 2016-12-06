package net.brainfuck.interpreter.compiler;

import net.brainfuck.common.RegexParser;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author davidLANG
 */
class MacroParser {
    private Map<String, Macro> macros = new HashMap<>();

    Map<String, Macro> getMacros() {
        return macros;
    }

    private String[] getArgument(String str) throws SyntaxErrorException {
        str = str.substring(0, str.length()-1);
        return str.isEmpty() ? null : str.split(",");
    }

    private boolean countainsSpace(String str) {
        return str.indexOf(' ') != -1 || str.indexOf('\t') != -1;
    }

    private void addArguments(Macro macro, String[] arguments) throws SyntaxErrorException {
        for (String argument :  arguments) {
            argument = argument.trim();
            if (this.countainsSpace(argument)) {
                throw new SyntaxErrorException("Argument macro countain space(s) '" + argument + "'");
            }
            if (argument.isEmpty()) {
                throw new SyntaxErrorException("Argument not found before ','");
            }
            macro.addArgument(argument);
        }
    }

    private String analyzeArguements(Macro macro, String str) throws SyntaxErrorException {
        String[] macroNameAndArgument = str.split("\\(");
        String name;
        String[] arguments;

        name = macroNameAndArgument[0];
        if (macroNameAndArgument.length == 2) {
            arguments = this.getArgument(macroNameAndArgument[1]);
            if (arguments != null) {
                this.addArguments(macro, arguments);
            }
        }
        return name;
    }

    /**
     * Save a macro.
     *
     * @param instruction the instruction
     * @throws SyntaxErrorException the syntax error exception
     */
    void saveMacro(String instruction) throws SyntaxErrorException {
        instruction = instruction.substring(1);
        String[] tmp = instruction.split("\\)");
        String macroNameAndArgument = tmp[0];
        Macro macro = new Macro();
        String name = analyzeArguements(macro, macroNameAndArgument);

        if (tmp.length != 2) {
            throw new SyntaxErrorException("Bad definition of macro '" + instruction + "'");
        }
        String[] definitions = tmp[1].split("\\s+");

        int length = definitions.length;
        for (int i = 0; i < length; i++) {
            if (macros.containsKey(definitions[i])) {
                i = addMacroInstructions(definitions, macro, length, i);
            } else {
                macro.addInstructions(getListOfInstruction(definitions[i]));
            }
        }
        macros.put(name, macro);
    }

    /**
     * Add macro Instructions to a macro
     * @param definitions the definitions of current macro
     * @param macro the macro to update
     * @param length the length of definition
     * @param i the current index loop
     * @return the new index loop
     */
    private int addMacroInstructions(String[] definitions, Macro macro, int length, int i) {
        if (i + 1 < length && RegexParser.isNumeric(definitions[i + 1])) {
            int nb = Integer.parseUnsignedInt(definitions[i + 1]);
            macro.addInstructions(getMacroInstructions(definitions[i++], nb));
        } else {
            macro.addInstructions(getMacroInstructions(definitions[i], 1));
        }
        return i;
    }

    /**
     * Read a string and return the list of instruction corresponding
     *
     * @param definitions definitions definition of a macro "++++"
     * @return List of instruction
     * @throws SyntaxErrorException when an instruction is unknown
     */
    private List<Language> getListOfInstruction(String definitions) throws SyntaxErrorException {
        Language language;
        List<Language> instrList = new ArrayList<>();

        if ((language = Language.languageMap.get(definitions)) != null) {
            instrList.add(language);
        } else {
            for (char inst : definitions.toCharArray()) {
                if ((language = Language.languageMap.get(Character.toString(inst))) == null) {
                    throw new SyntaxErrorException("Bad macro definition : " + definitions);
                }
                instrList.add(language);
            }
        }
        return instrList;
    }

    /**
     * Get list of instruction of a macro.
     *
     * @param definitions definition of a macro "++++"
     * @param nb the number of time the macro is call
     * @return List of instruction : N * List of intruction of a macro
     */
    private List<Language> getMacroInstructions(String definitions, int nb) {
        List<Language> instrList = new ArrayList<>();
        Macro macro = macros.get(definitions);

        List<Integer> arguments = new ArrayList<>();
        arguments.add(1);
        for (int cpt = 0; cpt < nb; cpt++) {
            instrList.addAll(macro.write(arguments));
        }
        return instrList;
    }

}
