package net.brainfuck.common.executable;

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
public class Procedure extends Executable {
    private final String name;
    Memory memory;

    /**
     * Constructs a default procedure.
     *
     * @param procedureName
     * @param instructions the instruction list f the function.
     * @param jumpTable    the jumpTable of the function.
     * @param memory       the memory of the program.
     */
    public Procedure(String procedureName, List<Language> instructions, JumpTable jumpTable, Memory memory) throws MemoryOutOfBoundsException {
        super(instructions, jumpTable);
        name = procedureName;
        this.memory = memory;
        memory.lock();
    }

    /**
     * @throws BracketsParseException
     * @see Executable
     */
    @Override
    public void closeReader() throws BracketsParseException, MemoryOutOfBoundsException {
        super.closeReader();
        memory.unlock(false);
    }
}
