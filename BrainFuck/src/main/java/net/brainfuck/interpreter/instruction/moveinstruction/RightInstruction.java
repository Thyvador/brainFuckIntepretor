package net.brainfuck.interpreter.instruction.moveinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;

/**
 * Representation of RIGHT instruction ">" "RIGHT".
 *
 * @author FooBar Team
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
    public void execute(Memory memory) throws MemoryOutOfBoundsException {
        memory.right();
    }

}
