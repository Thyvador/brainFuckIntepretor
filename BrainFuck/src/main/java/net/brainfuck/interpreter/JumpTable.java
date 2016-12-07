package net.brainfuck.interpreter;

import net.brainfuck.exception.BracketsParseException;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/**
 * The Class JumpTable.
 */
public class JumpTable {

	private boolean throwError = true;
	private Map<Integer, Integer> table = new HashMap<>();
	private Stack<Integer> jumpStack = new Stack<>();

	/**
	 * Instantiates a new jump table.
	 */
	public JumpTable(boolean throwError) {
		this.throwError = throwError;
	}

	/**
	 * Instantiates a new jump table.
	 *
	 */
	public JumpTable(){}

	/**
	 * Adds the instruction.
	 *
	 * @param currentInstruction the current instruction
	 * @param pos                the pos
	 * @throws BracketsParseException the brackets parse exception
	 */
	public void addInstruction(Language currentInstruction, int pos) throws BracketsParseException {
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
	public int getAssociated(int pos) {
		return table.get(pos);
	}



}
