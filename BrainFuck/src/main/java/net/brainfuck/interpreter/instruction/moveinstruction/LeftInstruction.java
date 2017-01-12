package net.brainfuck.interpreter.instruction.moveinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.SegmentationFaultException;
import net.brainfuck.interpreter.Language;

/**
 * Representation of LEFT instruction "<" "LEFT".
 *
 * @author FoBar Team
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
	 * @throws SegmentationFaultException 
	 */
	@Override
	public void execute(Memory memory) throws MemoryOutOfBoundsException, SegmentationFaultException {
		memory.left();
	}

}
