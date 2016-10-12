package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.IOException;


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
    public void execute(Memory machine) throws MemoryOverFlowException, MemoryOutOfBoundsException, IOException {

        try {
            int value = System.in.read();
            machine.set(value);
        } catch (java.io.IOException e) {
            throw new net.brainfuck.exception.IOException("IN : La lecture de caractère a échouée");
        }
    }
}
