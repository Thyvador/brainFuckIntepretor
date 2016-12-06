package net.brainfuck.interpreter.compiler;

import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * @author davidLANG
 */
public class Macro {
    private List<String>  argumentsName = new ArrayList<>();
    private List<List<Language>> listesInstructions = new ArrayList<>();


    void addArgument(String argument) throws SyntaxErrorException {
        if (argumentsName.contains(argument)) {
            throw new SyntaxErrorException("Argument already define : " + argument);
        }
        argumentsName.add(argument);
    }

    public void addInstruction(Language instruction) {
        List<Language> instructions = new ArrayList<>();
        instructions.add(instruction);
        listesInstructions.add(instructions);
    }

    void addInstructions(List<Language> instructions) {
        listesInstructions.add(instructions);
    }


    public List<Language> write(List<Integer>  values) {
        List<Language> macroExecution = new ArrayList<>();

        for (List<Language> instructions : listesInstructions) {
            macroExecution.addAll(instructions);
        }
        return macroExecution;
    }

}
