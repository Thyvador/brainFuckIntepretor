package net.brainfuck.interpreter;

import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/**
 * The Class JumpTable.
 * @author FoBar Team
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
	 * Adds the instruction.
	 *
	 * @param currentInstruction the current instruction
	 * @param pos                the pos
	 * @throws BracketsParseException the brackets parse exception
	 */
	public void addInstruction(AbstractInstruction currentInstruction, int pos) throws BracketsParseException {
		try {
			if (currentInstruction.equals(Language.JUMP.getInterpreter())) {
				jumpStack.add(pos);
			} else if (currentInstruction.equals(Language.BACK.getInterpreter())) {
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


	@Override
	public String toString() {
		return table.toString();
	}
}
