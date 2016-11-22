package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;


/**
 *  Representation of IN instruction "." "IN".
 *
 * @author François Melkonian IN_PATH
 */
public class InInstruction extends AbstractInstruction {

	/**
	 * Instantiates a new in instruction.
	 */
	public InInstruction() {
		super(Language.IN);
	}

    /**
	 * Execute "set" method from class Memory.
	 *
	 * @param argumentInstruction the argument instruction
	 * @throws MemoryOverFlowException the memory over flow exception
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 * @throws FileNotFoundIn the file not found in
	 */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOverFlowException, MemoryOutOfBoundsException, FileNotFoundIn {
        Memory memory = argumentInstruction.getMemory();

        int value;
        try {
            value = System.in.read();
        } catch (java.io.IOException e) {
            throw new FileNotFoundIn("can't be read");
        }
        if (value == -1) {
            throw new FileNotFoundIn("is finished");
        }
        memory.set(value);
    }

}
