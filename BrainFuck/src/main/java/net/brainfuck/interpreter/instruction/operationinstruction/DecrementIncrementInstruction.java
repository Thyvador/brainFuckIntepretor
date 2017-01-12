package net.brainfuck.interpreter.instruction.operationinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * @author FoBar Team
 */
abstract public class DecrementIncrementInstruction extends AbstractInstruction {

    DecrementIncrementInstruction(Language language) {
        super(language);
    }

    @Override
	abstract public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException;
}
