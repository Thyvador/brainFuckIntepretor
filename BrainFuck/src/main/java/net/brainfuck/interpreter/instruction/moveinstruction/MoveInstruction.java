package net.brainfuck.interpreter.instruction.moveinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Created by davidLANG on 14/12/2016.
 */
abstract public class MoveInstruction extends AbstractInstruction {

    MoveInstruction(Language language) {
        super(language);
    }

    abstract public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException;
}
