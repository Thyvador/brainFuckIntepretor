package net.brainfuck.common.executables;

import net.brainfuck.common.Logger;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

import java.util.List;
import java.util.Stack;

/**
 * The Executable class represents all the element in a program that can be executed.
 *
 * @author Alexandre HILTCHER
 */
public abstract class Executable {
    protected List<Language> instructions;
    protected int index = 0;
    protected Stack<Integer> marks;
    protected JumpTable jumpTable;
    protected Logger logger = Logger.getInstance();

    /**
     * Constructs a default Executable.
     *
     * @param instructions
     * @param jumpTable
     */
    public Executable(List<Language> instructions, JumpTable jumpTable) {
        this.instructions = instructions;
        this.jumpTable = jumpTable;
        marks = new Stack<>();
    }

    /**
     * Return the next instruction of the instructions list.
     *
     * @return the next instruction.
     */
    public Language getNext() {
        if (index >= instructions.size()) return null;
        Language instruction = instructions.get(index);
        logger.countMove();
        index++;
        return instruction;
    }

    /**
     * CLose the reader and check the brackets.
     *
     * @throws BracketsParseException
     */
    public void closeReader() throws BracketsParseException {
        if (!marks.isEmpty())
            throw new BracketsParseException();
    }

    /**
     * Add the index of the current bracket to the stack representing the brackets.
     */
    public void mark() {
        marks.push(index);
    }

    /**
     * Set the index to the value on top of the stack representing the brackets.
     *
     * @throws IOException
     * @throws BracketsParseException
     */
    public void reset() throws IOException, BracketsParseException {
        if (marks.isEmpty())
            throw new BracketsParseException("[");
        seek(marks.peek());
    }

    /**
     * Remove the element on top of the stack representing the brackets.
     *
     * @throws BracketsParseException
     */
    public void unmark() throws BracketsParseException {
        if (marks.isEmpty())
            throw new BracketsParseException("[");
        marks.pop();
    }

    /**
     * Set the index to the value corresponding to the current index in the jumpTable.
     */
    public void seek() {
        seek(jumpTable.getAssociated(index));
    }

    /**
     * Set the index to pos.
     *
     * @param pos the new value of the index.
     */
    private void seek(int pos) {
        index = pos;
    }

    /**
     * Return the execution pointer.
     *
     * @return the execution pointer.
     */
    public int getExecutionPointer() {
        return index;
    }

    /**
     * Return the marks stack. Should not be used (except for the tests).
     *
     * @return the marks stack.
     */
    public Stack<Integer> getMarks() {
        return marks;
    }
}