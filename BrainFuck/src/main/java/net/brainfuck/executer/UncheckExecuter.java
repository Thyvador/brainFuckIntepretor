package net.brainfuck.executer;

import net.brainfuck.common.*;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.InstructionInterface;

/**
 * Execute the AbstractInstruction command according to a context without "--rewrite" and "--check".
 *
 * @author davidLANG
 */
class UncheckExecuter implements ContextExecuter {

    /**
	 * Execute the AbstractInstruction command according to a context without "--rewrite" and "--check".
	 *
	 * @param i the AbstractCommand to execute
	 * @param memory the argument executer
	 * @throws MemoryOverFlowException throw by memory
	 * @throws IOException throw by reader
	 * @throws MemoryOutOfBoundsException throw by memory
	 * @throws FileNotFoundIn throw by reader
	 * @throws BracketsParseException throw by JumpInstruction or by BackInstruction
     * @throws SegmentationFaultException 
	 */
    @Override
    public void execute(InstructionInterface i, Memory memory, ExecutionReader reader) throws MemoryOverFlowException,
			IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
    	i.execute(memory);
    }
}

