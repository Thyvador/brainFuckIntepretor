package net.brainfuck.interpreter;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;

public class JumpTable {

	Map<Long, Long> table = new HashMap<>();
	
	public JumpTable(Reader reader) throws IOException, SyntaxErrorException, BracketsParseException {
		Stack<Long> jumpStack = new Stack<>();
		String instruction;
		Language currentInstruction;

        try {
			while ((instruction = reader.getNext()) != null) {
			    if ((currentInstruction = Language.languageMap.get(instruction)) == null) {
			        throw new SyntaxErrorException(instruction);
			    }
			    if (currentInstruction == Language.JUMP) {
			    	jumpStack.add(reader.getExecutionPointer());
			    } else if (currentInstruction == Language.BACK) {
			    	table.put(jumpStack.peek(), reader.getExecutionPointer());
			    	table.put(reader.getExecutionPointer(), jumpStack.pop());
			    }
			}
			if (!jumpStack.isEmpty())
				throw new BracketsParseException("]");
		} catch (EmptyStackException e) {
			throw new BracketsParseException("[");
		}
	}
	
	public long getAssociated(long pos) {
		return table.get(pos);
	}
	
}
