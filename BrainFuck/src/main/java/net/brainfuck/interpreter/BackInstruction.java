package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;

public class BackInstruction extends AbstractExecute {

	BackInstruction() {
		super(Language.BACK);
	}

	@Override
	public void execute(ArgumentInstruction argumentInstruction) throws BracketsParseException, IOException, MemoryOutOfBoundsException {
		if (argumentInstruction.getMemory().get() != 0) {
			argumentInstruction.getReader().reset();
		} else {
			argumentInstruction.getReader().unmark();
		}
	}

}
