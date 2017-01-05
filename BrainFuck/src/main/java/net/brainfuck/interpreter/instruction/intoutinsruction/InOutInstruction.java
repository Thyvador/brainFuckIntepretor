package net.brainfuck.interpreter.instruction.intoutinsruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * The class InOutInstruction is the mother class of the InInstruction and OutInstruction.
 *
 * @author Alexandre HILTCHER
 */
abstract public class InOutInstruction extends AbstractInstruction {


    public InOutInstruction(Language language) {
        super(language);
    }

    @Override
	abstract public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException;
}
