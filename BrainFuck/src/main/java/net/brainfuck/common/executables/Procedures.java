package net.brainfuck.common.executables;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

import java.util.List;

/**
 * The Functions class represents the functions that the can use.
 *
 * @author Alexandre HILTCHER
 */
public class Procedures extends Executable {

    /**
     * Constructs a default procedure.
     *
     * @param instructions the instruction list f the function.
     * @param jumpTable    the jumpTable of the function.
     * @param memory       the memory of the program.
     */
    public Procedures(List<Language> instructions, JumpTable jumpTable, Memory memory) {
        super(instructions, jumpTable);
        openScope(memory);
    }

    /**
     * Open a scope in the memory.
     *
     * @param memory the memory of the program.
     */
    private void openScope(Memory memory) {
        // TODO: 21/12/16 Add memmory.sopce() 
    }

    /**
     * @throws BracketsParseException
     * @see Executable
     */
    @Override
    public void closeReader() throws BracketsParseException {
        super.closeReader();
        // TODO: 21/12/16 Add memory.scopeclose
    }
}
