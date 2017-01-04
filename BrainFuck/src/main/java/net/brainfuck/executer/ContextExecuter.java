package net.brainfuck.executer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
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
     * @param i      the AbstractCommand to execute
     * @param memory the memory
     * @throws MemoryOverFlowException    throw by memory
     * @throws IOException                throw by reader
     * @throws MemoryOutOfBoundsException throw by memory
     * @throws FileNotFoundIn             throw by reader
     * @throws BracketsParseException     throw by JumpInstruction or by BackInstruction
     * @throws SegmentationFaultException throw by memory
     */
    void execute(InstructionInterface i, Memory memory, Executable reader) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException, SegmentationFaultException;
}

