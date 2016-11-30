package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ExcecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;

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
	 * @param memory the memory
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
    @Override
    public void execute(Memory memory, ExcecutionReader reader) throws MemoryOutOfBoundsException {
        memory.right();
    }

}
