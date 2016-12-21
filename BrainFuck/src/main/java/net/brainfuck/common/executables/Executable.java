package net.brainfuck.common.executables;

import net.brainfuck.common.Logger;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

import java.util.List;
import java.util.Stack;

/**
 * Created by thyvador on 21/12/16.
 */
public abstract class Executable {
    protected List<Language> instructions;
    protected int index = 0;
    protected Stack<Integer> marks;
    protected JumpTable jumpTable;
    protected Logger logger = Logger.getInstance();

    public Executable(List<Language> instructions, JumpTable jumpTable) {
        this.instructions = instructions;
        this.jumpTable = jumpTable;
        marks = new Stack<>();
    }

    public Language getNext() {
        if (index >= instructions.size()) return null;
        Language instruction = instructions.get(index);
        logger.countMove();
        index++;
        return instruction;
    }

    public void closeReader() throws BracketsParseException {
        if (!marks.isEmpty())
            throw new BracketsParseException();
    }

    public void mark() {
        marks.push(index);
    }

    public void reset() throws IOException, BracketsParseException {
        if (marks.isEmpty())
            throw new BracketsParseException("[");
        seek(marks.peek());
    }

    public void unmark() throws BracketsParseException {
        if (marks.isEmpty())
            throw new BracketsParseException("[");
        marks.pop();
    }

    public void seek() {
        seek(jumpTable.getAssociated(index));
    }

    private void seek(int pos) {
        index = pos;
    }

    public int getExecutionPointer() {
        return index;
    }

    public Stack<Integer> getMarks() {
        return marks;
    }

    public void setMarks(Stack<Integer> marks) {
        this.marks = marks;
    }
}