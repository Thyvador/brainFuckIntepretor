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
public class Function extends Executable {
    private final String name;
    private Memory memory;
    private List<String> argumentsName;
    private List<Integer> arguments;

    /**
     *
     * Constructs a default function.
     *
     * @param functionName the function name.
     * @param instructions the instruction list f the function.
     * @param jumpTable the jumpTable of the function.
     * @param memory the memory of the program.
     * @param arguments the list of arguments.
     */
    public Function(String functionName, List<Language> instructions, JumpTable jumpTable, Memory memory, List<String> arguments) {
        super(instructions, jumpTable);
        this.name = functionName;
        this.memory = memory;
        this.argumentsName = arguments;
    }

    /**
     * Constructs a default function.
     * @param instructions the instruction list f the function.
     * @param jumpTable the jumpTable of the function.
     * @param memory the memory of the program.
     */
    public Function(String procedurName, List<Language> instructions, JumpTable jumpTable, Memory memory) throws MemoryOutOfBoundsException {
        super(instructions, jumpTable);
        this.name = procedurName;
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
