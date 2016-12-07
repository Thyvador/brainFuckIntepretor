package net.brainfuck.interpreter.compiler;

import net.brainfuck.common.StringParser;
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



    private String getMacroName(String str) {
        return str.substring(1).split("\\(")[0];
    }

    private void addArguments(Macro macro, String[] arguments) throws SyntaxErrorException {
        for (String argument :  arguments) {
            argument = argument.trim();
            if (StringParser.containsSpace(argument)) {
                throw new SyntaxErrorException("Argument macro countain space(s) '" + argument + "'");
            }
            if (argument.isEmpty()) {
                throw new SyntaxErrorException("Argument not found before ','");
            }
            macro.addArgument(argument);
        }
    }

    private String analyzeArguements(Macro macro, String str) throws SyntaxErrorException {
        String name;
        name = this.getMacroName(str);
        if (name.isEmpty()) {
            throw new SyntaxErrorException("no name for macro " + str);
        }

        String[] arguments = StringParser.getArguments(str);
        if (arguments != null) {
            this.addArguments(macro, arguments);
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
        if (!StringParser.isCorrectParentheses(instruction)) {
            throw new SyntaxErrorException("bad parenthesis" + instruction);
        }
        if (!StringParser.containsSpace(instruction)) {
            throw new SyntaxErrorException("no instruction declared");
        }
        String nameAndArguments;
        String definition;
        String[] definitions;
        String name;
        Macro macro = new Macro();
        if (StringParser.containsParenthesis(instruction)) {
            nameAndArguments = instruction.substring(0, instruction.indexOf(')') + 1);
            definition = instruction.substring(instruction.indexOf(')') + 1);

            name = analyzeArguements(macro, nameAndArguments);

        } else {
            definition = instruction.substring(instruction.indexOf(" "));
            name = instruction.substring(1, instruction.indexOf(" "));
        }

        if (definition.isEmpty()) {
            throw new SyntaxErrorException("Bad definition of macro '" + instruction + "'");
        }
        definitions = StringParser.splitSpace(definition);

        int length = definitions.length;
        for (int i = 0; i < length; i++) {
            if (macros.containsKey(definitions[i])) {
                i = addMacroInstructions(definitions, macro, length, i);
            } else {
                i = addInstruction(definitions, macro, length, i);
            }
        }
        macros.put(name, macro);
    }

    private int addInstruction(String[] definitions, Macro macro, int length, int i) throws SyntaxErrorException {
        List<Language> instructions = this.getListOfInstruction(definitions[i]);

        if (i + 1 < length && macro.containsArgument(definitions[i+1])) {
            macro.addInstructionsArgument(instructions, definitions[i+1]);
            i += 1;
        } else {
            macro.addInstructions(instructions);
        }
        return i;
    }

    /**
     * Add macro Instructions to a macro
     * @param definitions the definitions of current macro
     * @param macro the macro to update
     * @param length the length of definition
     * @param i the current index loop
     * @return the new index loop
     */
    private int addMacroInstructions(String[] definitions, Macro macro, int length, int i) throws SyntaxErrorException {
        if (i + 1 < length && StringParser.isNumeric(definitions[i + 1])) {
            int nb = Integer.parseUnsignedInt(definitions[i + 1]);
            macro.addInstructions(getMacroInstructions(definitions[i++], nb));
        } else if (i + 1 < length && macro.containsArgument(definitions[i + 1])) {
            macro.addInstructionsArgument(getMacroInstructions(definitions[i++], 1), definitions[i + 1]);
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
    private List<Language> getMacroInstructions(String definitions, int nb) throws SyntaxErrorException {
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
