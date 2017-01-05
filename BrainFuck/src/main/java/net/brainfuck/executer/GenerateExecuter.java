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

public class GenerateExecuter implements ContextExecuter {

	private Writer writer;

	public GenerateExecuter(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void execute(InstructionInterface i, Memory memory, Executable reader)
			throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn,
			BracketsParseException, SegmentationFaultException {
		try {
			writer.write(i.generate());
			writer.flush();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

}
