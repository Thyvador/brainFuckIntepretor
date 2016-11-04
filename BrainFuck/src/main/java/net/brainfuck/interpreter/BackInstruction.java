package net.brainfuck.interpreter;

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
	public void execute(Memory memory, Reader reader) throws BracketsParseException, IOException, MemoryOutOfBoundsException {
		if (memory.get() != 0) {
			reader.reset();
		} else {
			reader.unmark();
		}
	}

}
