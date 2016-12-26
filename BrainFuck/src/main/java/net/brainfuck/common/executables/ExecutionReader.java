package net.brainfuck.common.executables;

import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

import java.util.List;

/**
 * The ExecutionReader represents the main program to be executed.
 *
 * @author ALexandre HILTCHER
 */
public class ExecutionReader extends Executable {

    /**
     * Constructs a default ExecutionReader.
     *
     * @param instructions the list of instructions.
     * @param jumpTable    the jumpTable.
     */
    public ExecutionReader(List<Language> instructions, JumpTable jumpTable) {
        super(instructions, jumpTable);
    }


}

