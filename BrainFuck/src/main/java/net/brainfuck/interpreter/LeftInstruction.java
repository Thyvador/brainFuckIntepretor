package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.exception.MemoryOutOfBoundsException;

// TODO: Auto-generated Javadoc
/**
 * The Class LeftInstruction.
 *
 * @author davidLANG
 */
public class LeftInstruction extends AbstractInstruction {
	
    /**
	 * Instantiates a new left instruction.
	 */
    public LeftInstruction() {
		super(Language.LEFT);
	}

	/**
	 * Execute the "left" method of Memory Class.
	 *
	 * @param argumentInstruction
	 *            the argument instruction
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException {
        argumentInstruction.getMemory().left();
    }

}
