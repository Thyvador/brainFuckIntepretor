package net.brainfuck.executer;

import net.brainfuck.common.ArgumentExecuter;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.InstructionInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class RewriteExecuter.
 *
 * @author davidLANG
 */
class RewriteExecuter implements ContextExecuter {


    /**
	 * Execute the AbstractExecute command according to the "--rewrite" context.
	 *
	 * @param i
	 *            the AbstractCommand to execute
	 * @param argumentExecuter
	 *            the argument executer
	 * @throws MemoryOverFlowException
	 *             throw by memory
	 * @throws IOException
	 *             throw by reader
	 * @throws MemoryOutOfBoundsException
	 *             throw by memory
	 */
    @Override
    public void execute(InstructionInterface i, ArgumentExecuter argumentExecuter) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException {
        i.rewrite();
    }
}
