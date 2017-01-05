package net.brainfuck.interpreter.processing;

import net.brainfuck.common.StringParser;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;

import java.util.*;

/**
 * @author davidLANG
 */
class MacroParser {
    private Map<String, Macro> macros = new HashMap<>();

    Map<String, Macro> getMacros() {
        return macros;
    }


    private String getMacroNameInArgument(String str) {
        if (StringParser.containsParenthesis(str) && StringParser.isCorrectParentheses(str)) {
            return str.substring(0, str.indexOf("(")).trim();
        }
        return null;
    }

    private String getMacroName(String str) throws SyntaxErrorException {
        String name;

        if (StringParser.containsParenthesis(str) &&
                !StringParser.containsSpace(str.substring(0, str.indexOf("(")))) {
            name = str.substring(0, str.indexOf('('));
        } else {
            name = str.substring(0, str.indexOf(" "));
        }

        if (name.isEmpty()) {
            throw new SyntaxErrorException("no name for macro " + str);
        }
        return name;
    }

    private String[] getArguments(String instruction) {
        if (StringParser.containsParenthesis(instruction) &&
                instruction.indexOf(' ') > instruction.indexOf('(')) {
            return StringParser.getArguments(instruction);
        }
        return null;
    }

    private void addArguments(Macro macro, String[] arguments) throws SyntaxErrorException {
        for (String argument : arguments) {
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

    private void addArguementsToMacro(Macro macro, String[] arguments) throws SyntaxErrorException {
        if (arguments != null) {
            this.addArguments(macro, arguments);
        }
    }


    private void checkCorrectSyntax(String instruction) throws SyntaxErrorException {
        if (!StringParser.isCorrectParentheses(instruction) || !StringParser.containsSpace(instruction)) {
            throw new SyntaxErrorException("bad parenthesis" + instruction);
        }
    }

    /**
     * Save a macro.
     *
     * @param instruction the instruction
     * @throws SyntaxErrorException the syntax error exception
     */
    void saveMacro(String instruction) throws SyntaxErrorException {

        // Vérifie que la macro est bien parenthésé et contient des instructions à sauvegarder
        this.checkCorrectSyntax(instruction);

        // Création d'une nouvelle macro
        // Analyze des argument de la macro (ce qui est entre parenthèse) et ajout ceux ci dans la macro
        // Récupération du nom et de la définition de la macro
        Macro macro = new Macro();
        addArguementsToMacro(macro, this.getArguments(instruction));
        String name = this.getMacroName(instruction.substring(1));
        String definition = getDefinition(instruction);
        String[] definitions = StringParser.splitSpace(definition.trim());

        // Ajout des instruction dans la macro
        int length = definitions.length;
        for (int i = 0; i < length; i++) {

            if (macros.containsKey(definitions[i])) {
                i = addMacroInstructions(definitions, macro, length, i);
            } else if (macros.containsKey(this.getMacroNameInArgument(definitions[i]))) {
                i = addMacroArgumentInstruction(definitions, macro, length, i);
            } else {
                i = addInstruction(definitions, macro, length, i);
            }
        }
        macros.put(name, macro);
    }


    private String getDefinition(String instruction) throws SyntaxErrorException {
        String definition;

        if (StringParser.containsParenthesis(instruction) &&
                !StringParser.containsSpace(instruction.substring(0, instruction.indexOf("(")))) {
            definition = instruction.substring(instruction.indexOf(')') + 1);
        } else {
            definition = instruction.substring(instruction.indexOf(" "));
        }

        if (definition.isEmpty()) {
            throw new SyntaxErrorException("Bad definition of macro '" + instruction + "'");
        }
        return definition;
    }

    private int addInstruction(String[] definitions, Macro macro, int length, int i) throws SyntaxErrorException {
        List<Language> instructions = this.getListOfInstruction(definitions[i]);

        if (i + 1 < length && macro.containsArgument(definitions[i + 1])) {
            macro.addInstructionsArgument(instructions, definitions[i + 1]);
            i += 1;
        } else {
            macro.addInstructions(instructions);
        }
        return i;
    }

    /**
     * Add macro Instructions to a macro
     *
     * @param definitions the definitions of current macro
     * @param macro       the macro to update
     * @param length      the length of definition
     * @param i           the current index loop
     * @return the new index loop
     */
    private int addMacroArgumentInstruction(String[] definitions, Macro macro, int length, int i) throws SyntaxErrorException {
        if (!StringParser.isCorrectParentheses(definitions[i])) {
            throw new SyntaxErrorException("Bad argument " + definitions[i]);
        }

        String arguments[] = StringParser.getArguments(definitions[i]);
        String name = this.getMacroNameInArgument(definitions[i]);
        if (i + 1 < length && StringParser.isNumeric(definitions[i + 1])) {
            int nb = Integer.parseUnsignedInt(definitions[i + 1]);
            macro.addMacroInstruction(macros.get(name), Arrays.asList(arguments), nb);
            i += 1;
        } else if (i + 1 < length && macro.containsArgument(definitions[i + 1])) {
            macro.addMacroInstruction(macros.get(name), Arrays.asList(arguments), definitions[i + 1]);
            i = +1;
        } else {
            macro.addMacroInstruction(macros.get(name), Arrays.asList(arguments), 1);
        }


        return i;
    }


    /**
     * Add macro Instructions to a macro
     *
     * @param definitions the definitions of current macro
     * @param macro       the macro to update
     * @param length      the length of definition
     * @param i           the current index loop
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
     * @param nb          the number of time the macro is call
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
