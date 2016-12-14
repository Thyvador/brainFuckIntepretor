package net.brainfuck.interpreter.instruction.operationinstruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Created by davidLANG on 14/12/2016.
 */
abstract public class DecrementIncrementInstruction extends AbstractInstruction {

    DecrementIncrementInstruction(Language language) {
        super(language);
    }

    abstract public void execute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException;
}
