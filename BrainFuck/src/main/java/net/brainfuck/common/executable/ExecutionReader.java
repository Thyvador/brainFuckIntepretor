package net.brainfuck.common.executable;

import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

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
    public ExecutionReader(List<AbstractInstruction> instructions, JumpTable jumpTable) {
        super(null, instructions, jumpTable, null);
    }

	@Override
	public String generate() {
		return name + getArgumentString() + ";";
	}


}

