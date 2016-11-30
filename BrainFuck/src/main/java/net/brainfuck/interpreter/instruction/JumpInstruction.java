package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 *  Representation of JUMP instruction "[" "JUMP".
 */
public class JumpInstruction extends AbstractInstruction {
	JumpTable jumpTable;
	/**
	 * Instantiates a new jump instruction.
	 */
	public JumpInstruction(JumpTable jumpTable) {
		super(Language.JUMP);
		this.jumpTable = jumpTable;
	}

	/**
	 * Execute the nonLinear approch of JUMP instruction.
	 *
	 * @param memory the memory
	 * @throws MemoryOutOfBoundsException throw by memory
	 * @throws IOException throw by memory
	 */
	@Override
	public void execute(Memory memory, Reader reader) throws MemoryOutOfBoundsException, IOException {
		// Reach corresponding closing bracket
		if (memory.get() == 0) {
			reader.seek(jumpTable.getAssociated(reader.getExecutionPointer()));
		}
	}

}
