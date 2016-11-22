package net.brainfuck.executer;

import net.brainfuck.common.ArgumentExecuter;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.InstructionInterface;

/**
 * Execute the AbstractExecute command according to the "--translate" context.
 *
 * @author davidLANG
 */
class TranslateExecuter implements ContextExecuter {

    /**
	 * Execute the AbstractExecute command according to the "--translate" context.
	 *
	 * @param i the AbstractCommand to execute
	 * @param argumentExecuter the argument executer
	 * @throws MemoryOverFlowException throw by memory
	 * @throws IOException throw by reader
	 * @throws MemoryOutOfBoundsException throw by memory
	 */
    @Override
    public void execute(InstructionInterface i, ArgumentExecuter argumentExecuter) throws MemoryOverFlowException,
			IOException, MemoryOutOfBoundsException {
        argumentExecuter.getImageWriter().write(i.translate());
    }
}
