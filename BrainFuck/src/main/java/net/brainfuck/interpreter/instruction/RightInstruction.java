package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Representation of RIGHT instruction ">" "RIGHT".
 *
 * @author davidLANG
 */
public class RightInstruction extends AbstractInstruction {
	
    /**
	 * Instantiates a new right instruction.
	 */
    public RightInstruction() {
		super(Language.RIGHT);
	}

	/**
	 * Execute the "right" method of Memory Class.
	 *
	 * @param argumentInstruction the argument instruction
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException {
        argumentInstruction.getMemory().right();
    }

}
