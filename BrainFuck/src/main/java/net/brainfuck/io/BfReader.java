package net.brainfuck.io;

import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Read file with long and short syntax blended.
 *
 * @author : Francois Melkonian
 */
public class BfReader implements Reader {

	public static final int PREPROCESSING = '!';
	private static final int CR = '\r';
	private static final int TAB = '\t';
	private static final int COMMENT = '#';
	private static final int LF = '\n';
	private static final int SPACE = ' ';
	private static final int EOF = -1;
	private static final List<Integer> ignoredCharacters = Arrays.asList(CR, LF, TAB, SPACE);

	private String next = null;
	private RandomAccessFile reader;
	private Stack<Integer> marks;
	private int oldvar = LF;


	/**
	 * Constructs argumentAnalyzer BfReader from file name.
	 * Support long and short syntax
	 *
	 * @param filename name of the file.
	 * @throws FileNotFoundException if the file doesn't exist
	 */

	public BfReader(String filename) throws FileNotFoundException {
		try {
			reader = new RandomAccessFile(filename, "r");
			marks = new Stack<>();
		} catch (java.io.IOException e) {
			throw new FileNotFoundException(filename);
		}
	}


	/**
	 * Read the line just after the pointer on the file and put it on "next" string.
	 *
	 * @param val
	 *            the current value
	 * @throws java.io.IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void readUntilEndOfLine(int val) throws java.io.IOException {
		boolean comment = false;
		int c = reader.read();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Character.toString((char) val));

		while (!isNewLine(c) && c != EOF && !comment) {
			if (isComment(c))
				comment = true;
			stringBuilder.append(Character.toString((char) c));
			c = reader.read();
		}
		c = ignoreComment();
		next = stringBuilder.toString().trim();
		oldvar = c;
	}

	private boolean isIgnoredCharacter(int c) {
		return ignoredCharacters.contains(c);
	}

	private int ignoreCharacters(int c) throws java.io.IOException {
		oldvar = c;
		while (isIgnoredCharacter(c)) {
			c = reader.read();
		}
		return c;
	}


	/**
	 * Skip all character until new line or end of file character is read.
	 *
	 * @return the current caracter : new line or end of file
	 * @throws java.io.IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private int ignoreComment() throws java.io.IOException {
		int c;
		c = reader.read();
		while (!isNewLine(c) && c != EOF) {
			c = reader.read();
		}
		return c;
	}

	/**
	 * Ignore space,comments and new line char
	 *
	 * @param nextVal
	 *            the next val
	 * @return the int
	 * @throws java.io.IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private int ignore(int nextVal) throws java.io.IOException {
		boolean end = false;

		while (!end) {
			if (isComment(nextVal)) {
				nextVal = ignoreComment();
			} else if (isIgnoredCharacter(nextVal)){
				nextVal = ignoreCharacters(nextVal);
			} else {
				end = true;
			}
		}
		return nextVal;
	}


	/**
	 * Get the next instruction.
	 * If the next instruction seems long, it
	 *
	 * @return the next instruction.
	 * @throws IOException if file close during reading.
	 */
	@Override
	public String getNext() throws IOException {
		try {
			// Ignore space, tab, newLine, commentary
			int nextVal = reader.read();
			nextVal = this.ignore(nextVal);

			return  (!isEndOfFile(nextVal)) ? this.getInstruction(nextVal) : null;
		} catch (java.io.IOException e) {
			throw new IOException();
		}

	}

	/**
	 * Check if is the end of file
	 * @param val the character to check
	 * @return true if is the end of file
	 */
	private boolean isEndOfFile(int val) {
		return val == EOF;
	}

	private String getInstruction(int nextVal) throws java.io.IOException {
		if (isLong(nextVal) && isNewLine(oldvar)) {
			return getLongInstruction(nextVal);
		}
		return getShortInstruction(nextVal);
	}

	/**
	 * Get the next Short Instruction
	 *
	 * @param nextVal the current val read
	 * @return String represent the short instruction
	 */
	private String getShortInstruction(int nextVal) {
		oldvar = nextVal;
		return Character.toString((char) nextVal);
	}

	/**
	 * Get the next Long Instruction
	 *
	 * @param nextVal the current val read
	 * @return String represent the long instruction
	 */
	private String getLongInstruction(int nextVal) throws java.io.IOException {
		readUntilEndOfLine(nextVal);
		return next;
	}

	/**
	 * Get the next Macro. Warning if it's not a macro return the next instruction
	 *
	 * @return the next Macro or next Instruction if not macro
	 * @throws IOException if file close during reading.
	 */
	public String getNextMacro() throws IOException {
		try {
			int nextVal = reader.read();
			nextVal = ignore(nextVal);
			if (isMacro(nextVal)) {
				readUntilEndOfLine(nextVal);
				return next;
			}

			return !isEndOfFile(nextVal) ? this.getInstruction(nextVal) : null;
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	private boolean isMacro(int nextVal) {
		return nextVal == PREPROCESSING;
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#getExecutionPointer()
	 */
	@Override
	public int getExecutionPointer() throws IOException {
		try {
			return (int) reader.getFilePointer();
		} catch (java.io.IOException e) {
			throw new IOException("Impossible to access file pointer");
		}
	}



	/**
	 * Check if argumentAnalyzer character is argumentAnalyzer '\n' or '\r'.
	 *
	 * @param nextVal the character to test.
	 * @return true if the char is an end of line char, else false.
	 */
	private boolean isNewLine(int nextVal) {
		return nextVal == CR || nextVal == LF;
	}

	/**
	 * Checks if is comment.
	 *
	 * @param nextVal
	 *            the next val
	 * @return true, if is comment
	 */
	private boolean isComment(int nextVal) {
		return nextVal == COMMENT;
	}

	/**
	 * Check if the character start argumentAnalyzer "long" syntax or not.
	 *
	 * @param nextVal the first character of argumentAnalyzer line.
	 * @return true if the line may contain argumentAnalyzer "long" syntax.
	 */
	private boolean isLong(int nextVal) {
		return (nextVal >= 'A' && nextVal <= 'Z') ||(nextVal >= 'a' && nextVal <= 'z');
	}


	/**
	 * Close the file when the reader finished him.
	 *
	 * @throws IOException            if file can't close.
	 * @throws BracketsParseException if the stack of bracket is not empty
	 */
	@Override
	public void closeReader() throws IOException, BracketsParseException {
		try {
			reader.close();
			if (!marks.isEmpty())
				throw new BracketsParseException("]");
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#mark()
	 */
	@Override
	public void mark() throws IOException {
		try {
			marks.push((int) reader.getFilePointer());
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#reset()
	 */
	@Override
	public void reset() throws IOException, BracketsParseException {
		if (marks.isEmpty())
			throw new BracketsParseException("[");
		seek(marks.peek());
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#unmark()
	 */
	@Override
	public void unmark() throws BracketsParseException {
		if (marks.isEmpty())
			throw new BracketsParseException("[");
		marks.pop();
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Reader#seek(long)
	 */
	@Override
	public void seek(int pos) throws IOException {
		try {
			reader.seek(pos);
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Gets the marks.
	 *
	 * @return the marks
	 */
	Stack<Integer> getMarks() {
		return marks;
	}
}

