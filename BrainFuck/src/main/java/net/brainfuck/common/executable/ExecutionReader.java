package net.brainfuck.common.executable;

import java.util.ArrayList;
import java.util.List;

import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * The ExecutionReader represents the main program to be executed.
 *
 * @author FoobarTeam
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

	/**
	 * Return the string representing the C suite of instruction of the procedure.
	 *
	 * @return the string representing the instructions of the executable.
	 */
	@Override
	public String generate() {
		StringBuilder stringBuilder = new StringBuilder().append(String.format("int %s () {\n",
				name));
		stringBuilder.append("int *ptr = memory;\nconst int *start_scope=ptr;\n\n");
		for (AbstractInstruction instr: instructions)
			stringBuilder.append(instr.generate());
		return stringBuilder.append("\nreturn 0;\n}\n\n").toString();
	}


}

