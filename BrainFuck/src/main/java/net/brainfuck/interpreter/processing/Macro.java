package net.brainfuck.interpreter.processing;

import net.brainfuck.common.Pair;
import net.brainfuck.common.StringParser;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * @author davidLANG
 */
public class Macro {


    private int nbArgument = 0;
    private List<String> argumentsName = new ArrayList<>();
    private List<Pair<List<Language>, String>> currentList = new ArrayList<>();
    private List<Pair<Language, String>> listesInstructions = new ArrayList<>();


    Macro() {
        listesInstructions.add(new Pair(currentList, null));
    }

    void addArgument(String argument) throws SyntaxErrorException {
        if (argumentsName.contains(argument)) {
            throw new SyntaxErrorException("Argument already define : " + argument);
        }
        argumentsName.add(argument);
        this.nbArgument += 1;
    }


    void addInstructionsArgument(List<Language> instructions, String argument) {
        currentList.add(new Pair<>(instructions, argument));
    }

    void addMacroInstruction(Macro macro, List<String> arguments, int nbexecute) throws SyntaxErrorException {
        for (int i = 0; i < nbexecute; i++) {
            addMacroInstruction(macro, arguments, null);
        }
    }

    void addMacroInstruction(Macro macro, List<String> arguments, String argumentMacro) throws SyntaxErrorException {
        List<Pair<Language, String>> macroPairs = macro.listesInstructions;

        currentList = new ArrayList<>();
        listesInstructions.add(new Pair(currentList, argumentMacro));
        if (arguments.size() != macro.nbArgument) {
            String error = (arguments.size() > nbArgument) ? "too much argument" : "not enought argument";
            throw new SyntaxErrorException(error);
        }

        String argument;
        int nbExecute;
        for (Pair pair : macroPairs) {
            for (Pair pairInstruction : (List<Pair>) pair.getFirst()) {
                argument = pairInstruction.getSecond() != null ? arguments.get(macro.argumentsName.indexOf(pairInstruction.getSecond())) : null;
                if (argument != null && !argumentsName.contains(argument)) {
                    if (StringParser.isNumeric(argument)) {
                        nbExecute = Integer.parseUnsignedInt(argument);
                        for (int i = 0; i < nbExecute; i++)
                            currentList.add(new Pair<List<Language>, String>((List<Language>) pairInstruction.getFirst(), null));
                    } else {
                        throw new SyntaxErrorException("Unrecognyze argument " + argument);

                    }
                } else {
                    currentList.add(new Pair<List<Language>, String>((List<Language>) pairInstruction.getFirst(), argument));
                }
            }
        }

    }

    void addInstructions(List<Language> instructions) {
        currentList.add(new Pair<>(instructions, null));
    }

    public List<Language> write() throws SyntaxErrorException {
        return write(null);
    }

    public List<Language> write(List<Integer> values) throws SyntaxErrorException {
        List<Language> macroExecution = new ArrayList<>();
        int nbExecuteInstructions;

        this.checkArgument(values);

        for (Pair instructions : listesInstructions) {
            nbExecuteInstructions = instructions.getSecond() == null ? 1 : this.getArgumentValue(values, (String) instructions.getSecond());
            for (int i = 0; i < nbExecuteInstructions; i++) {
                macroExecution.addAll(writePairs((List<Pair>) instructions.getFirst(), values));
            }

        }
        return macroExecution;
    }

    private void checkArgument(List<Integer> values) throws SyntaxErrorException {
        if ((values == null && nbArgument != 0) || values.size() != nbArgument) {
            String error = (values == null || values.size() > nbArgument) ? "too much argument" : "not enought argument";
            throw new SyntaxErrorException(error);
        }
    }

    private List<Language> writePairs(List<Pair> pairs, List<Integer> values) {
        int nbExecute;
        List<Language> instructions = new ArrayList<>();

        for (Pair pair : pairs) {
            nbExecute = pair.getSecond() == null ? 1 : this.getArgumentValue(values, (String) pair.getSecond());
            for (int j = 0; j < nbExecute; j++) {
                instructions.addAll((List<Language>) pair.getFirst());
            }
        }
        return instructions;
    }

    private int getArgumentValue(List<Integer> values, String argument) {
        return values.get(argumentsName.indexOf(argument));
    }


    boolean containsArgument(String argument) {
        return argumentsName.contains(argument);
    }


}
