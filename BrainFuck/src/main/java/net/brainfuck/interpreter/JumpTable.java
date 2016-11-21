package net.brainfuck.interpreter;

import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// TODO: Auto-generated Javadoc

/**
 * The Class JumpTable.
 */
public class JumpTable {

	private boolean throwError = true;
	private Map<Long, Long> table = new HashMap<>();
	private Stack<Long> jumpStack = new Stack<>();

	/**
	 * Instantiates a new jump table.
	 */
	public JumpTable(boolean throwError) {
		this.throwError = throwError;
	}

	/**
	 * Instantiates a new jump table.
	 *
	 * @param reader the reader
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws SyntaxErrorException   the syntax error exception
	 * @throws BracketsParseException the brackets parse exception
	 */
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
			if (!jumpStack.isEmpty() && throwError)
				throw new BracketsParseException("]");
		} catch (EmptyStackException e) {
			if (throwError) {
				throw new BracketsParseException("[");

			}
		}
	}

	/**
	 * Adds the instruction.
	 *
	 * @param currentInstruction the current instruction
	 * @param pos                the pos
	 * @throws BracketsParseException the brackets parse exception
	 */
	public void addInstruction(Language currentInstruction, long pos) throws BracketsParseException {
		try {
			if (currentInstruction == Language.JUMP) {
				jumpStack.add(pos);
			} else if (currentInstruction == Language.BACK) {
				table.put(jumpStack.peek(), pos);
				table.put(pos, jumpStack.pop());
			}
		} catch (EmptyStackException e) {
			if (throwError) {
				throw new BracketsParseException("[");

			}
		}
	}

	/**
	 * Finish.
	 *
	 * @throws BracketsParseException the brackets parse exception
	 */
	public void finish() throws BracketsParseException {
		if (!jumpStack.isEmpty())
			throw new BracketsParseException("]");
	}

	/**
	 * Gets the associated.
	 *
	 * @param pos the pos
	 * @return the associated
	 */
	public long getAssociated(long pos) {
		return table.get(pos);
	}

}
