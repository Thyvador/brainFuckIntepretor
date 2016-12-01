package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.Language;


/**
 * Representation of Decrement instruction "-" "DECR".
 *
 * @author davidLANG
 */
public class DecrementInstruction extends AbstractInstruction {

	/**
	 * Instantiates a new decrement instruction.
	 */
	public DecrementInstruction() {
		super(Language.DECR);
	}

	/**
	 * Execute "decr" method from class Memory.
	 *
	 * @param memory the memory
	 * @throws MemoryOverFlowException    the memory over flow exception
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
	@Override
	public void execute(Memory memory, ExecutionReader reader) throws MemoryOverFlowException, MemoryOutOfBoundsException {
		memory.decr();
	}

}
