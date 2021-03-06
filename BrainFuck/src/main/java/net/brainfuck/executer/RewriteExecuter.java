package net.brainfuck.executer;

import java.io.Writer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SegmentationFaultException;
import net.brainfuck.interpreter.instruction.InstructionInterface;

/**
 * Execute the AbstractInstruction command according to the "--rewrite" context.
 *
 * @author FooBar Team
 */
class RewriteExecuter implements ContextExecuter {

	private Writer writer;

	public RewriteExecuter(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void execute(InstructionInterface i, Memory memory, Executable reader)
			throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn,
			BracketsParseException, SegmentationFaultException {
		try {
			writer.write(i.rewrite());
			writer.flush();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}
}
