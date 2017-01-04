package net.brainfuck.common.executable;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Pair;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Interpreter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

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
    protected Executable parent;
    protected Logger logger = Logger.getInstance();
    protected List<String> argument;


    /**
     * Constructs a default Executable.
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

    /**
     * Return the list of arguments.
     *
     * @return the arguments
     */
    public List<String> getArgument() {
        return argument;
    }

    /**
     * Return a string containing the list of arguments.
     *
     * @return the string containing the list of arguments.
     */
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

    /**
     * Execute the list of instructions representing the executable.
     *
     * @param memory the memory
     * @throws MemoryOutOfBoundsException
     * @throws MemoryOverFlowException
     * @throws IOException
     * @throws FileNotFoundIn
     * @throws BracketsParseException
     * @throws SegmentationFaultException
     */
    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        parent = Language.getExecutable();
        Language.setExecutable(this);
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            instruction.execute(memory);
        }
        Language.setExecutable(parent);
    }

    /**
     * Print the list of short syntax of the commands .
     *
     * @return the list of instructions of the executable.
     */
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
     * Return the list of colors (in hexa) which represent the instructions.
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

    /**
     * Return the string representing the C suite of instruction of the executable.
     *
     * @return the string representing the instructions of the executable.
     */
    @Override
    public String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : argument)
            stringBuilder.append(String.format("(*(ptr++)) = %s;", arg));
        stringBuilder.append("\n");
        for (AbstractInstruction instr : instructions)
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
        parent = Language.getExecutable();
        Language.setExecutable(this);
        AbstractInstruction instruction;
        while ((instruction = getNext()) != null) {
            instruction.trace(memory, reader);
        }
        Language.setExecutable(parent);
    }

    /**
     * Return the jump table of the current executable.
     *
     * @return the jump table.
     */
    public JumpTable getJumpTable() {
        return jumpTable;
    }

    public String getName() {
        return name;
    }
}