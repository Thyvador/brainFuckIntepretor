package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ExcecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
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
	public void execute(Memory memory, ExcecutionReader reader) throws BracketsParseException, IOException, MemoryOutOfBoundsException {
		if (memory.get() != 0) {
			reader.seek(jumpTable.getAssociated(reader.getExecutionPointer()));
		}
	}


}
