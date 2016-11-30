package net.brainfuck.common;

import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.InstructionInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Alexandre on 30/11/2016.
 */
public class ExcecutionReader {
	private List<Language> instructions;
	private int index = 0;
	private Stack<Integer> marks;

	public ExcecutionReader() {
		this.instructions = new ArrayList<>();
		marks = new Stack<>();
	}

	public void addInstruction(Language instruction) {
		instructions.add(instruction);
	}


	public AbstractInstruction getNext() {
		if (index >= instructions.size()) return null;
		return instructions.get(index).getInterpreter();
	}

	public long getExecutionPointer() throws IOException {
		return index;
	}

	public void closeReader() throws BracketsParseException {
		if (!marks.isEmpty())
			throw new BracketsParseException();
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#mark()
	 */
	public void mark() {
		marks.push(index);
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#reset()
	 */
	public void reset() throws IOException, BracketsParseException {
		if (marks.isEmpty())
			throw new BracketsParseException("[");
		seek(marks.peek());
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#unmark()
	 */
	public void unmark() throws BracketsParseException {
		if (marks.isEmpty())
			throw new BracketsParseException("[");
		marks.pop();
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#seek(long)
	 */
	public void seek(long pos) {
		seek(pos);
	}

	/**
	 * Gets the marks.
	 *
	 * @return the marks
	 */
	public Stack<Integer> getMarks() {
		return marks;
	}

}

