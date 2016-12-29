package net.brainfuck.common.executable;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;

import java.util.List;
import java.util.Stack;

/**
 * The Executable class represents all the element in a program that can be executed.
 *
 * @author Alexandre HILTCHER
 */
public abstract class Executable extends AbstractInstruction {
    protected final String name;
    protected List<AbstractInstruction> instructions;
    protected int index = -1;
    protected Stack<Integer> marks;
    protected JumpTable jumpTable;
    protected Logger logger = Logger.getInstance();
    protected List<String> argument;


    /**
     * Constructs a default Executable.
     *
     * @param instructions
     * @param jumpTable
     */
    public Executable(String name, List<AbstractInstruction> instructions, JumpTable jumpTable, List<String> argument) {
        super();
        Language.addInstruction(this, name);
        this.name = name;
        this.instructions = instructions;
        this.jumpTable = jumpTable;
        this.argument = argument;
        marks = new Stack<>();
    }

    /**
     * Return the next instruction of the instructions list.
     *
     * @return the next instruction.
     */
    public AbstractInstruction getNext() {
        index++;
        if (index >= instructions.size()) return null;
        AbstractInstruction instruction = instructions.get(index);
        logger.countMove();
        return instruction;
    }

    public List<String> getArgument() {
	    return argument;
	}
    
    public String getArgumentString() {
	    StringBuilder res = new StringBuilder().append("(");
	    for (String arg : argument) {
			res.append(arg).append(",");
		}
	    return res.append(")").toString();
	}

	/**
     * CLose the reader and check the brackets.
     *
     * @throws BracketsParseException
     */
    public void closeReader() throws BracketsParseException, MemoryOutOfBoundsException {
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

    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            instruction.execute(memory);
        }
    }

    public String rewrite() {
        StringBuilder stringBuilder = new StringBuilder();
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            stringBuilder.append(instruction.rewrite());
        }
        return stringBuilder.toString();
    }

    /**
     * Return the color (in hexa) which represent the instruction
     *
     * @return String hexa wich represent the color of the current instruction
     */
    @Override
    public String translate() {
        StringBuilder stringBuilder = new StringBuilder();
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            stringBuilder.append(instruction.translate());
        }
        return stringBuilder.toString();
    }

    @Override
    public abstract String generate();

    /**
     * Execute the instruction and write the trace.
     *
     * @param memory the memory
     * @throws IOException                throw by inReader
     * @throws MemoryOutOfBoundsException throw by memory
     * @throws BracketsParseException     throw by interpreter
     * @throws MemoryOverFlowException    throw by memory
     * @throws FileNotFoundIn             throw by writer
     * @throws SegmentationFaultException
     */
    @Override
    public void trace(Memory memory, Executable reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn, SegmentationFaultException {
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            instruction.execute(memory);
            logger.write(reader.getExecutionPointer(), memory);
        }
    }

}