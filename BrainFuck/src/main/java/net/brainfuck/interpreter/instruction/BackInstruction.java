package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;


/**
 * Representation of Back instruction "]" "BACK".
 */
public class BackInstruction extends AbstractInstruction {
	JumpTable jumpTable;

	/**
	 * Instantiates a new back instruction.
	 */
	public BackInstruction(JumpTable jumpTable) {
		super(Language.BACK);
		this.jumpTable = jumpTable;
	}

	/**
	 * Execute the instruction back
	 *
	 * @param memory the memory
	 * @throws BracketsParseException     the memory out of bounds exception
	 * @throws IOException                Signals that an I/O exception has occurred.
	 * @throws MemoryOutOfBoundsException the brackets parse exception
	 */
	@Override
	public void execute(Memory memory, Reader reader) throws BracketsParseException, IOException, MemoryOutOfBoundsException {
		if (memory.get() != 0) {
			reader.seek(jumpTable.getAssociated(reader.getExecutionPointer()));
		}
	}

}
