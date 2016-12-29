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
    private List<Short> values;
    private Executable compositeInstruction;

    public ProcedureFunctionExecute(List<Short> values, Executable compositeInstruction) throws SyntaxErrorException {
        checkNumberOfArgument(values, compositeInstruction);
        this.values = values;
        this.compositeInstruction = compositeInstruction;
    }

    private void checkNumberOfArgument(List<Short> values, Executable compositeInstruction) throws SyntaxErrorException {
        int size1 = values.size();
        int size2 = compositeInstruction.getArgument() != null ?
                compositeInstruction.getArgument().size() : -1;
        if (size1 != size2)
            throw new SyntaxErrorException("wrong number of argument");
    }

    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        memory.setArguments(values);
        compositeInstruction.execute(memory);
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
        return compositeInstruction.generate();
    }

}
