package net.brainfuck.common.executable;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.List;

/**
 * Class wich execute a procedure or a function with his argument.
 *
 * @author davidLANG
 */
public class ProcedureFunctionExecute extends AbstractInstruction {
    private List<Integer> values;
    private Executable compositeInstruction;

    /**
     * Constructor
     *
     * @param values parameter value
     * @param compositeInstruction the function or the procedure to execute
     * @throws SyntaxErrorException if not the right number of argument is given for the compositeInstruction
     */
    public ProcedureFunctionExecute(List<Integer> values, Executable compositeInstruction) throws SyntaxErrorException {
        checkNumberOfArgument(values, compositeInstruction);
        this.values = values;
        this.compositeInstruction = compositeInstruction;
    }

    /**
     * Check if the list of value and the list of argument in compositeInstruction
     * countain the same amount of value
     *
     * @param values2 the valuea of argument
     * @param compositeInstruction function or procedure
     * @throws SyntaxErrorException if the amount of value of two list are not the same
     */
    private void checkNumberOfArgument(List<Integer> values2, Executable compositeInstruction) throws SyntaxErrorException {
        int size1 = values2.size();
        int size2 = compositeInstruction.getArgument() != null ?
                compositeInstruction.getArgument().size() : -1;
        if (size1 != size2)
            throw new SyntaxErrorException("wrong number of argument");
    }

    /**
     * Execute all the instruction in compositeInstruction.
     *
     * @param memory the memory
     * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException MemoryOutOfBoundsException }
     * @throws MemoryOverFlowException {@link MemoryOverFlowException MemoryOverFlowException}
     * @throws IOException {@link IOException IOException}
     * @throws FileNotFoundIn {@link FileNotFoundIn FileNotFoundIn}
     * @throws BracketsParseException {@link BracketsParseException BracketsParseException }
     * @throws SegmentationFaultException {@link SegmentationFaultException SegmentationFaultException }
     */
    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        memory.lock();
        memory.setArguments(values);
        compositeInstruction.execute(memory);
    }

    /**
     *  Trace  all instruction of the composite instruction
     *
     * @param memory the memory
     * @param reader the reader
     * @throws IOException {@link IOException IOException}
     * @throws MemoryOutOfBoundsException  {@link MemoryOutOfBoundsException MemoryOutOfBoundsException }
     * @throws BracketsParseException  {@link BracketsParseException BracketsParseException }
     * @throws MemoryOverFlowException {@link MemoryOverFlowException MemoryOverFlowException}
     * @throws FileNotFoundIn {@link FileNotFoundIn FileNotFoundIn}
     * @throws SegmentationFaultException {@link SegmentationFaultException SegmentationFaultException }
     */
    @Override
    public void trace(Memory memory, Executable reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn, SegmentationFaultException {
        memory.lock();
        memory.setArguments(values);
        compositeInstruction.trace(memory, reader);
    }

    /**
     * Rewrite all instruction of the compositeInstruction
     *
     * @return the rewrite String of all instruction in compositeInstruction
     */
    @Override
    public String rewrite() {
        return compositeInstruction.rewrite();
    }

    /**
     * Translate all instruction of the compositeInstruction
     *
     * @return the translate String of all instruction
     */
	@Override
	public String translate() {
		return compositeInstruction.translate();
	}

    /**
     * Generate the language C string corresponding of all instruction
     * in compositeInstruction
     *
     * @return the language C string corresponding of all instruction in compositeInstruction
     */
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
