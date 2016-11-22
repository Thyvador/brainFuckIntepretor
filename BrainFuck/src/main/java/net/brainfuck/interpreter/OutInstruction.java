package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

// TODO: Auto-generated Javadoc
/**
 * The Class OutInstruction.
 *
 * @author François Melkonian
 */
public class OutInstruction extends AbstractInstruction {

    /**
	 * Instantiates a new out instruction.
	 */
    public OutInstruction() {
		super(Language.OUT);
	}

	/**
	 * Print the value on current index, call "get" method from class Memory.
	 *
	 * @param argumentInstruction
	 *            the argument instruction
	 * @throws MemoryOverFlowException
	 *             the memory over flow exception
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        System.out.print( (char)argumentInstruction.getMemory().get());
    }

}
