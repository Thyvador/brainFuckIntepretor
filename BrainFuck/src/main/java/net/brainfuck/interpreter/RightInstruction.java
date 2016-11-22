package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.exception.MemoryOutOfBoundsException;

// TODO: Auto-generated Javadoc
/**
 * The Class RightInstruction.
 *
 * @author davidLANG
 */
public class RightInstruction extends AbstractExecute {
	
    /**
	 * Instantiates a new right instruction.
	 */
    public RightInstruction() {
		super(Language.RIGHT);
	}

	/**
	 * Execute the "right" method of Memory Class.
	 *
	 * @param argumentInstruction
	 *            the argument instruction
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException {
        argumentInstruction.getMemory().right();
    }

}
