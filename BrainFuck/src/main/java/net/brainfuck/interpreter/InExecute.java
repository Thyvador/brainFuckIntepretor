package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.IOException;


/**
 * @author François Melkonian
 *         IN_PATH
 */
class InExecute extends AbstractExecute {

	InExecute() {
		super(Language.IN);
	}
	
    /**
     * Execute "" method from class Memory
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) throws MemoryOverFlowException, MemoryOutOfBoundsException, FileNotFoundIn {

        int value;
        try {
            value = System.in.read();
        } catch (java.io.IOException e) {
            throw new net.brainfuck.exception.FileNotFoundIn("IN_PATH : La lecture de caractère a échouée");
        }
        if (value == -1) {
            throw new net.brainfuck.exception.FileNotFoundIn("IN_PATH : La lecture de caractère a échouée");
        }
        machine.set(value);

    }

}
