package net.brainfuck.common.executable;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidLANG on 28/12/2016.
 */
public class ProcedureFunctionExecute extends AbstractInstruction {
    private List<Integer> values;
    private Executable compositeInstruction;

    public ProcedureFunctionExecute(List<Integer> values, Executable compositeInstruction) throws SyntaxErrorException {
        checkNumberOfArgument(values, compositeInstruction);
        this.values = values;
        this.compositeInstruction = compositeInstruction;
    }

    private void checkNumberOfArgument(List<Integer> values2, Executable compositeInstruction) throws SyntaxErrorException {
        int size1 = values2.size();
        int size2 = compositeInstruction.getArgument() != null ?
                compositeInstruction.getArgument().size() : -1;
        if (size1 != size2)
            throw new SyntaxErrorException("wrong number of argument");
    }

    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        memory.lock();
        memory.setArguments(values);
        compositeInstruction.execute(memory);
    }

    @Override
    public void trace(Memory memory, Executable reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn, SegmentationFaultException {
        memory.lock();
        memory.setArguments(values);
        super.trace(memory, reader);
    }

    @Override
    public String rewrite() {
        return compositeInstruction.rewrite();
    }

	@Override
	public String translate() {
		return compositeInstruction.translate();
	}

	@Override
	public String generate() {
		StringBuilder res = new StringBuilder("ptr++;");
		if (compositeInstruction.getClass() == Function.class)
			res.append("(*ptr) = ");
		res.append(compositeInstruction.name).append("(");
		res.append("ptr");
		for (Integer v : values) {
			res.append(String.format(", memory[%d]", v));
		}
	    return res.append(");").toString();
	}

}