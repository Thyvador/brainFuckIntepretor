package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.SyntaxErrorException;

public class JumpExecute extends AbstractExecute {

	JumpExecute() {
		super(Language.JUMP);
	}

	@Override
	public void execute(Memory memory, Reader reader) throws MemoryOutOfBoundsException, IOException {
		if (memory.get() == 0) {
			reader.mark();
		} else {
			int cpt = 1;
			String instruction;
			while ((instruction = reader.getNext()) != null) {
	            if ((interpretor = this.interpretorExecuter.get(instruction)) == null) {
	                throw new SyntaxErrorException(instruction);
	            }
			}
		}
	}

}
