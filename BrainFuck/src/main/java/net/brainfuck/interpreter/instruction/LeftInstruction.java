package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;

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
	 * @param memory the memory
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
	@Override
	public void execute(Memory memory, Reader reader) throws MemoryOutOfBoundsException {
		memory.left();
	}

}
