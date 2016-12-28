package net.brainfuck.executer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.instruction.InstructionInterface;

/**
 * Execute the AbstractInstruction command according to the "--rewrite" context.
 *
 * @author davidLANG
 */
class RewriteExecuter implements ContextExecuter {


	/**
	 * Execute the AbstractInstruction command according to the "--rewrite" context.
	 *
	 * @param i      the AbstractCommand to execute
	 * @param memory the argument executer
	 * @throws MemoryOverFlowException    throw by memory
	 * @throws IOException                throw by reader
	 * @throws MemoryOutOfBoundsException throw by memory
	 */
	@Override
	public void execute(InstructionInterface i, Memory memory, Executable reader) throws MemoryOverFlowException,
IOException, MemoryOutOfBoundsException {
		i.rewrite();
	}
}
