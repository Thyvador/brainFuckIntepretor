package net.brainfuck.common;

import java.io.RandomAccessFile;
import java.util.Stack;
import java.util.StringTokenizer;

import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * Read file with long and short syntax blended.
 *
 * @author : Francois Melkonian
 */
public class BfReader implements Reader {
	
	public static final int PREPROCESSING = '!';
	
	private String next = null;
	private RandomAccessFile reader;
	private Stack<Long> marks;
	private boolean firstLine = true;
	private static final int CR = '\r';
	private static final int TAB = '\t';
	private static final int COMMENT = '#';
	private static final int LF = '\n';
	private static final int SPACE = ' ';
	private static final int EOF = -1;
	private int oldvar;
	private boolean checkPreprocessing = true;

	/**
	 * Constructs a BfReader from file name.
	 * Support long and short syntax
	 *
	 * @param filename name of the file.
	 * @throws FileNotFoundException if the file doesn't exist
	 */

	public BfReader(String filename) throws FileNotFoundException {
		try {
			reader = new RandomAccessFile(filename, "r");
			Logger.countInstruction((int)reader.length());
			marks = new Stack<>();
		} catch (java.io.IOException e) {
			throw new FileNotFoundException(filename);
		}
	}


	/**
	 * Read the line just after the pointer on the file
	 * and put it on "next" string.
	 *
	 * @param val the current value
	 * @throws java.io.IOException if IO error, it will be catch in getNext().
	 */
	private void readUntilEndOfLine(int val) throws java.io.IOException {
		next = Character.toString((char) val);
		boolean comment = false;
		int c = reader.read();
		while (!isNewLine(c) && c != EOF) {
			if (isComment(c))
				comment = true;
			if (!comment)
				next += Character.toString((char) c);
			c = reader.read();
		}
		StringTokenizer tok = new StringTokenizer(next, "\t ");
		next = tok.nextToken();
		oldvar = c;
	}

	/**
	 * Skip new line character(s).
	 * End of line character change according OS.
	 *
	 * @return The first character of the line.
	 * @throws java.io.IOException if IO error, it will be catch in getNext().
	 */
	private int ignoreNewLineChar() throws java.io.IOException {
		int c;
		while (isNewLine(c = reader.read())) ;
		oldvar = LF;
		return c;
	}

	/**
	 * Skip all character until new line or end of file character is read.
	 *
	 * @return the current caracter : new line or end of file
	 * @throws java.io.IOException if IO error, it will be catch in getNext().
	 */
	private int ignoreComment() throws java.io.IOException {
		int c;
		c = reader.read();
		while (!isNewLine(c) && c != EOF) c = reader.read();
		return c;
	}

	/**
	 * Skip all space and tabulation charecter until another charecter is read.
	 *
	 * @return the current charecter read :  could be everything except space or tabulation
	 * @throws java.io.IOException if IO error, it will be catch in getNext().
	 */
	private int ignoreSpace() throws java.io.IOException {
		int c;
		c = reader.read();
		while (isSpace(c) && c != EOF) c = reader.read();
		return c;
	}

	/**
	 *
	 *
	 * @param nextVal
	 * @return
	 * @throws java.io.IOException
	 */
	private int ignore(int nextVal) throws java.io.IOException {
		boolean end = false;

		while (!end) {
			if (isComment(nextVal)) {
				nextVal = ignoreComment();
			} else if (isSpace(nextVal)) {
				nextVal = ignoreSpace();
			} else if (isNewLine(nextVal)){
				nextVal = ignoreNewLineChar();
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
			int nextVal = reader.read();

			// Ignore space, tab, newLine, commentary
			nextVal = this.ignore(nextVal);

			if (nextVal == EOF) {
				return null;
			}
			
			if(checkPreprocessing && isPreprocessing(nextVal)) {
				readUntilEndOfLine(nextVal);
				return next;
			}
			checkPreprocessing = false;
			if (isLong(nextVal)) {
				if (firstLine) {
					firstLine = false;
					readUntilEndOfLine(nextVal);
					return next;
				} else if (isNewLine(oldvar)) {
					readUntilEndOfLine(nextVal);
					return next;
				}
			}
			if (firstLine) {
				firstLine = false;
			}
			oldvar = nextVal;
			return Character.toString((char) nextVal);

		} catch (java.io.IOException e) {
			throw new IOException();
		}

	}


	@Override
	public long getExecutionPointer() throws IOException {
		try {
			return reader.getFilePointer();
		} catch (java.io.IOException e) {
			throw new IOException("Impossible to access file pointer");
		}
	}

	private boolean isPreprocessing(int nextVal) {
		return nextVal == PREPROCESSING;
	}


	/**
	 * Check if a character is a '\n' or '\r'.
	 *
	 * @param nextVal the character to test.
	 * @return true if the char is an end of line char, else false.
	 */
	private boolean isNewLine(int nextVal) {
		return nextVal == CR || nextVal == LF;
	}

	private boolean isComment(int nextVal) {
		return nextVal == COMMENT;
	}

	private boolean isSpace(int nextVal) {
		return nextVal == TAB || nextVal == SPACE;
	}

	/**
	 * Check if the character start a "long" syntax or not.
	 *
	 * @param nextVal the first character of a line.
	 * @return true if the line may contain a "long" syntax.
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

	@Override
	public void mark() throws IOException {
		try {
			marks.push(reader.getFilePointer());
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	@Override
	public void reset() throws IOException, BracketsParseException {
		if (marks.isEmpty())
			throw new BracketsParseException("[");
		seek(marks.peek());
	}

	@Override
	public void unmark() throws BracketsParseException {
		if (marks.isEmpty())
			throw new BracketsParseException("[");
		marks.pop();
	}
	
	@Override
	public void seek(long pos) throws IOException {
		try {
			reader.seek(pos);
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}
}

