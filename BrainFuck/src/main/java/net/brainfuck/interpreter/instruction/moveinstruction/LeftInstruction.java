package net.brainfuck.interpreter.instruction.moveinstruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Representation of LEFT instruction "<" "LEFT".
 *
 * @author davidLANG
 */
public class LeftInstruction extends MoveInstruction {

	/**
	 * Instantiates a new left instruction.
	 */
	public LeftInstruction() {
		super(Language.LEFT);
	}

	/**
	 * Execute the "left" method of Memory Class.
	 *
	 * @param memory the memory
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
	@Override
	public void execute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException {
		memory.left();
	}

}
