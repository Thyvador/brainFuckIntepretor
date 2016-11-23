package net.brainfuck.executer;

import net.brainfuck.common.ArgumentExecuter;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.InstructionInterface;

/**
 * The Interface ContextExecuter.
 *
 * @author davidLANG
 */
public interface ContextExecuter {
    
    /**
	 * Execute the AbstractExecute command according to the context.
	 *
	 * @param i
	 *            the AbstractCommand to execute
	 * @param args
	 *            the args
	 * @throws MemoryOverFlowException
	 *             throw by memory
	 * @throws IOException
	 *             throw by reader
	 * @throws MemoryOutOfBoundsException
	 *             throw by memory
	 * @throws FileNotFoundIn
	 *             throw by reader
	 * @throws BracketsParseException
	 *             throw by JumpInstruction or by BackInstruction
	 */
    void execute(InstructionInterface i, ArgumentExecuter args) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException;
}

