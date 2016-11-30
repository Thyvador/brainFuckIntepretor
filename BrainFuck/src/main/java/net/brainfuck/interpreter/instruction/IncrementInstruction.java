package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;


/**
 *  Representation of Back instruction "+" "INCR".
 *
 * @author davidLANG
 */
public class IncrementInstruction extends AbstractInstruction {

    /**
	 * Instantiates a new incremante instruction.
	 */
    public IncrementInstruction() {
		super(Language.INCR);
	}

	/**
	 * Execute the "incr" method of Memory Class.
	 *
	 * @param memory the memory
	 * @throws MemoryOverFlowException the memory over flow exception
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
    @Override
    public void execute(Memory memory, Reader reader) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        memory.incr();
    }

}
