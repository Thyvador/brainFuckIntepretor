package net.brainfuck.interpreter.instruction.jumpbackinstruction;

import javax.management.NotificationListener;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;


/**
 * Representation of Back instruction "]" "BACK".
 */
public class BackInstruction extends JumpBackInstruction {

	/**
	 * Instantiates a new back instruction.
	 */
	public BackInstruction(JumpTable jumpTable) {
		super(Language.BACK, jumpTable);
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
	public void execute(Memory memory, ExecutionReader reader) throws BracketsParseException, IOException, MemoryOutOfBoundsException {
		nonLinearExecute(memory, reader);
	}

	private void linearExecute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		if (memory.get() != 0) {
			reader.reset();
		} else {
			reader.unmark();
		}
	}

	private void nonLinearExecute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, IOException {
		if (memory.get() != 0) {
			reader.seek(jumpTable.getAssociated(reader.getExecutionPointer()));
		}
	}


}
