package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

import java.io.IOException;

/**
 * @author  François Melkonian
 * IN
 */
class InExecute implements InterpreterInterface {

    /**
     * Execute "" method from class Memory
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        try {
            int value = System.in.read();
            machine.set(value);
        } catch (IOException e) {
           e.printStackTrace();
            System.err.println("La lecture de caractère a échouée"); // ne devrait jamais arriver
            // throw new net.brainfuck.exception.IOException("La lecture de caractère a échouée");
        }
    }
}
