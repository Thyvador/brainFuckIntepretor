package net.brainfuck.executer;

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
 * The Interface ContextExecuter.
 *
 * @author FoBar Team
 */
public interface ContextExecuter {

	/**
	 * Execute the AbstractExecute command according to the context.
	 *
	 * @param i    the AbstractCommand to execute
	 * @param memory the memory
	 * @throws MemoryOverFlowException    throw by memory
	 * @throws IOException                throw by reader
	 * @throws MemoryOutOfBoundsException throw by memory
	 * @throws FileNotFoundIn             throw by reader
	 * @throws BracketsParseException     throw by JumpInstruction or by BackInstruction
	 * @throws SegmentationFaultException 
	 */
	void execute(InstructionInterface i, Memory memory, Executable reader) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException, SegmentationFaultException;
}

