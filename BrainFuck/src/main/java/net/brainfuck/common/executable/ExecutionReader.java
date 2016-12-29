package net.brainfuck.common.executable;

import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.ArrayList;
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
        super("main", instructions, jumpTable, new ArrayList<>());
    }

	@Override
	public String generate() {
		StringBuilder stringBuilder = new StringBuilder().append(String.format("int %s %s {\n",
				name, getArgumentString()));
		stringBuilder.append("int memory[30000] = {};\n");
		stringBuilder.append("int *ptr = *memory;\n");
		for (AbstractInstruction instr: instructions)
			stringBuilder.append(instr.generate());
		return stringBuilder.append("return 0;\n}\n\n").toString();
	}


}

