package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.IOException;


/**
 * @author François Melkonian
 *         IN_PATH
 */
public class InInstruction extends AbstractExecute {

	public InInstruction() {
		super(Language.IN);
	}

    /**
     * Execute "" method from class Memory
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOverFlowException, MemoryOutOfBoundsException, FileNotFoundIn {
        Memory memory = argumentInstruction.getMemory();

        int value;
        try {
            value = System.in.read();
        } catch (java.io.IOException e) {
            throw new FileNotFoundIn("IN_PATH : La lecture de caractère a échouée");
        }
        if (value == -1) {
            throw new FileNotFoundIn("IN_PATH : La lecture de caractère a échouée");
        }
        memory.set(value);
    }

}
