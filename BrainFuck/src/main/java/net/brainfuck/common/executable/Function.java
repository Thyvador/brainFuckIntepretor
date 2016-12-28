package net.brainfuck.common.executable;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
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
    public Function(String functionName, List<Language> instructions, JumpTable jumpTable, Memory memory) {
        super(functionName, instructions, jumpTable);
        this.memory = memory;
    }

    public void start() throws MemoryOutOfBoundsException {
        memory.lock();
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
}
