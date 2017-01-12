package net.brainfuck.interpreter.instruction.moveinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * @author FoBar Team
 */
abstract public class MoveInstruction extends AbstractInstruction {

    MoveInstruction(Language language) {
        super(language);
    }

    @Override
	abstract public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException;
}
