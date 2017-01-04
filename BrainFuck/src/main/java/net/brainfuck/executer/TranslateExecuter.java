package net.brainfuck.executer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.instruction.InstructionInterface;
import net.brainfuck.io.BfImageWriter;

/**
 * Execute the AbstractInstruction command according to the "--translate" context.
 *
 * @author davidLANG
 */
class TranslateExecuter implements ContextExecuter {
	private BfImageWriter writer;

	public TranslateExecuter(BfImageWriter writer) {
		this.writer = writer;
	}

	/**
	 * Execute the AbstractInstruction command according to the "--translate" context.
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
		writer.write(i.translate());
	}
}
