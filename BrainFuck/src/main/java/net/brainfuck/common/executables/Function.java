package net.brainfuck.common.executables;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

import java.util.List;

/**
 * The Function class represents the functions that the can use.
 *
 * @author Alexandre HILTCHER
 */
public class Function extends Executable {
    Memory memory;
    /**
     * Constructs a default function.
     * @param instructions the instruction list f the function.
     * @param jumpTable the jumpTable of the function.
     * @param memory the memory of the program.
     */
    public Function(List<Language> instructions, JumpTable jumpTable, Memory memory) throws MemoryOutOfBoundsException {
        super(instructions, jumpTable);
        this.memory = memory;
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
