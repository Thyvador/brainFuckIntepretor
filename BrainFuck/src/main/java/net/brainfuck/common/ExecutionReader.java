package net.brainfuck.common;

import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.List;
import java.util.Stack;

/**
 * Created by Alexandre on 30/11/2016.
 */
public class ExecutionReader {
	private List<Language> instructions;
	private int index = 0;
	private Stack<Integer> marks;
	private Logger logger = Logger.getInstance();

	public ExecutionReader(List<Language> instructions) {
		this.instructions = instructions;
		marks = new Stack<>();
	}


	public Language getNext() {
		if (index >= instructions.size()) return null;
		Language instruction = instructions.get(index);
		logger.countMove();
		index++;
		return instruction;
	}

	public int getExecutionPointer() throws IOException {
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
	public void seek(int pos) {

		index = pos;
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

