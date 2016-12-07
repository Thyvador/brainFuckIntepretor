package net.brainfuck.interpreter.compiler;

import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * @author davidLANG
 */
public class Macro {

    private class Pair {
        List<Language> instructions;
        String argument;

        Pair(List<Language> instructions, String argument) {
            this.instructions = instructions;
            this.argument = argument;
        }
    }

    private int nbArgument = 0;
    private List<String>  argumentsName = new ArrayList<>();
    private List<Pair> listesInstructions = new ArrayList<>();

    void addArgument(String argument) throws SyntaxErrorException {
        if (argumentsName.contains(argument)) {
            throw new SyntaxErrorException("Argument already define : " + argument);
        }
        argumentsName.add(argument);
        this.nbArgument += 1;
    }

    public void addInstruction(Language instruction) {
        List<Language> instructions = new ArrayList<>();
        instructions.add(instruction);

        listesInstructions.add(new Pair(instructions, null));
    }

    void addInstructionsArgument(List<Language> instructions, String argument) {
        listesInstructions.add(new Pair(instructions, argument));
    }

    void addInstructions(List<Language> instructions) {
        listesInstructions.add(new Pair(instructions, null));
    }

    public List<Language> write() throws SyntaxErrorException {
        return write(null);
    }

    public List<Language> write(List<Integer>  values) throws SyntaxErrorException {
        List<Language> macroExecution = new ArrayList<>();
        int nbExecute;

        if ((values == null && nbArgument != 0) || values.size() != nbArgument) {
            throw new SyntaxErrorException("not enought argument in macro");
        }

        for (Pair pair : listesInstructions) {
            nbExecute = pair.argument == null ? 1 : this.getArgumentValue(values, pair.argument);
            for (int i = 0; i < nbExecute; i++) {
                macroExecution.addAll(pair.instructions);
            }
        }
        return macroExecution;
    }

    private int getArgumentValue(List<Integer> values, String argument) {
        return values.get(argumentsName.indexOf(argument));
    }


    boolean containsArgument(String argument) {
        return argumentsName.contains(argument);
    }


}
