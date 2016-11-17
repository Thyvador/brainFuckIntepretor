package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;

public class BackInstruction extends AbstractExecute {

	public BackInstruction() {
		super(Language.BACK);
	}

	@Override
	public void execute(ArgumentInstruction argumentInstruction) throws BracketsParseException, IOException, MemoryOutOfBoundsException {
		nonLinearExecute(argumentInstruction);
	}
	
	private void nonLinearExecute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		Reader reader = argumentInstruction.getReader();
		
		if (argumentInstruction.getMemory().get() != 0) {
			reader.seek(argumentInstruction.getJumpTable().getAssociated(reader.getExecutionPointer()));
		}
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void linearExecute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		if (argumentInstruction.getMemory().get() != 0) {
			argumentInstruction.getReader().reset();
		} else {
			argumentInstruction.getReader().unmark();
		}
	}

}
