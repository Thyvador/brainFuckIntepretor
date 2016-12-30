package net.brainfuck.common.executable;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.List;

/**
 * The Function class represents the functions that the can use.
 *
 * @author Alexandre HILTCHER
 */
public class Procedure extends Executable {
    private Memory memory;

    /**
     * Constructs a default procedure.
     *
     * @param procedureName the procedure name
     * @param instructions the instruction list f the function.
     * @param jumpTable    the jumpTable of the function.
     * @param memory       the memory of the program.
     * @throws MemoryOutOfBoundsException
     */
    public Procedure(String procedureName, List<AbstractInstruction> instructions, JumpTable jumpTable, Memory memory, List<String> argument){
        super(procedureName, instructions, jumpTable, argument);
        this.memory = memory;
    }



    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        super.execute(memory);
        closeReader();
    }

    @Override
    public void trace(Memory memory, Executable reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn, SegmentationFaultException {
        super.trace(memory, reader);
        closeReader();
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

	@Override
	public String generate() {
		StringBuilder stringBuilder = new StringBuilder().append(String.format("void %s %s {\n\n",
				name, getArgumentString()));
		stringBuilder.append(super.generate());
		return stringBuilder.append("\n}\n\n").toString();
	}
}
