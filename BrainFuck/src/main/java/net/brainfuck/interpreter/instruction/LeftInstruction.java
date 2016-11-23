package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Representation of LEFT instruction "<" "LEFT".
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
	 * @param argumentInstruction the argument instruction
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException {
        argumentInstruction.getMemory().left();
    }

}