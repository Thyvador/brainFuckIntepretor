package net.brainfuck.common.executable;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.InstructionInterface;

import java.util.List;

/**
 * The Function class represents the functions that the can use.
 *
 * @author Alexandre HILTCHER
 */
public class Function extends Executable {
    private Memory memory;

    /**
     * Constructs a default function.
     * @param instructions the instruction list f the function.
     * @param jumpTable the jumpTable of the function.
     * @param memory the memory of the program.
     */
    public Function(String functionName, List<AbstractInstruction> instructions, JumpTable jumpTable, Memory memory, List<String> argument) {
        super(functionName, instructions, jumpTable, argument);
        this.memory = memory;
    }

    public void start() throws MemoryOutOfBoundsException {
        memory.lock();
    }

    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        start();
        super.execute(memory);
        memory.unlock(true);
    }

    @Override
    public void trace(Memory memory, Executable reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn, SegmentationFaultException {
        start();
        super.trace(memory, reader);
        memory.unlock(true);
    }

    /**
     * @see Executable
     * @throws BracketsParseException
     */
    @Override
    public void closeReader() throws BracketsParseException, MemoryOutOfBoundsException {
        super.closeReader();
        memory.unlock(true);
    }

	@Override
	public String generate() {
        return name + getArgumentString() + ";";
	}
}
