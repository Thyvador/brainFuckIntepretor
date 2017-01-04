package net.brainfuck.common.executable;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Pair;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Interpreter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.JumpInstruction;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * The Executable class represents all the element in a program that can be executed.
 *
 * @author Alexandre HILTCHER
 */
public abstract class Executable extends AbstractInstruction {
	
	private static List<Executable> executableRegistry = new LinkedList<>();
	
    protected final String name;
    protected List<AbstractInstruction> instructions;
    protected int index = -1;
    protected Stack<Integer> marks;
    protected JumpTable jumpTable;
    protected JumpTable parentJumpTable;
    protected Logger logger = Logger.getInstance();
    protected List<String> argument;


    /**
     * Constructs a default Executable.
     *
     */
    public Executable(String name, List<String> argument) {
        super();
        this.name = name;
        this.argument = argument;
        marks = new Stack<>();
        executableRegistry.add(this);
    }

    /**
     * Constructs a default Executable.
     *
     * @param instructions
     * @param jumpTable
     */
    public Executable(String name, List<AbstractInstruction> instructions, JumpTable jumpTable, List<String> argument) {
        this(name, argument);
        this.jumpTable = jumpTable;
        this.instructions = instructions;
    }

    public void addPair(Pair<List<AbstractInstruction>, JumpTable> pair) {
        this.jumpTable = pair.getSecond();
        this.instructions = pair.getFirst();
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
	    StringBuilder res = new StringBuilder().append("(int *ptr");
	    for (String arg : argument) {
	    	res.append(", int ").append(arg);
		}
	    return res.append(")").toString();
	}

	/**
	 * @return the executableRegistry
	 */
	public static List<Executable> getExecutableRegistry() {
		return executableRegistry;
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
        parentJumpTable = Interpreter.getCurrentJumpTable();
        Interpreter.setCurrentJumpTable(jumpTable);
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            instruction.execute(memory);
        }
        Interpreter.setCurrentJumpTable(parentJumpTable);
    }

    @Override
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
    public String generate() {
    	StringBuilder stringBuilder = new StringBuilder();
		for (String arg: argument)
			stringBuilder.append(String.format("(*(ptr++)) = %s;", arg));
		stringBuilder.append("\n");
		for (AbstractInstruction instr: instructions)
			stringBuilder.append(instr.generate());
		return stringBuilder.toString();
    }

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
        parentJumpTable = Interpreter.getCurrentJumpTable();
        Interpreter.setCurrentJumpTable(jumpTable);
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            instruction.execute(memory);
            logger.write(reader.getExecutionPointer(), memory);
        }
        Interpreter.setCurrentJumpTable(parentJumpTable);
    }

    public JumpTable getJumpTable() {
        return jumpTable;
    }
}