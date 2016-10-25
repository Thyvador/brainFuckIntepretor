package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.SyntaxErrorException;

public class JumpExecute extends AbstractExecute {

	JumpExecute() {
		super(Language.JUMP);
	}

	@Override
	public void execute(Memory memory, Reader reader) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		if (memory.get() != 0) {
			reader.mark();
		} else {
			int cpt = 1;
			Language instruction;
			while(cpt > 0) {
				instruction = Language.languageMap.get(reader.getNext());
				if (instruction == null) {
					throw new BracketsParseException("]");
				}
				if (instruction == Language.JUMP) {
	                cpt++;
	            } else if (instruction == Language.BACK) {
	            	cpt--;
	            }
			}
			// Reach corresponding closing bracket
		}
	}

}
