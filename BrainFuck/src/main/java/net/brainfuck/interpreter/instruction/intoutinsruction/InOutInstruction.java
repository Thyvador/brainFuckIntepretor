package net.brainfuck.interpreter.instruction.intoutinsruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Created by davidLANG on 14/12/2016.
 */
abstract public class InOutInstruction extends AbstractInstruction {

    public InOutInstruction(Language language) {
        super(language);
    }

    abstract public void execute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException;
}
