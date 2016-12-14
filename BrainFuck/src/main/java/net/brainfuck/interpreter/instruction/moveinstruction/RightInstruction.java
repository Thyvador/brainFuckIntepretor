package net.brainfuck.interpreter.instruction.moveinstruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Representation of RIGHT instruction ">" "RIGHT".
 *
 * @author davidLANG
 */
public class RightInstruction extends MoveInstruction {
	
    /**
	 * Instantiates a new right instruction.
	 */
    public RightInstruction() {
		super(Language.RIGHT);
	}

	/**
	 * Execute the "right" method of Memory Class.
	 *
	 * @param memory the memory
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
    @Override
    public void execute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException {
        memory.right();
    }

}
