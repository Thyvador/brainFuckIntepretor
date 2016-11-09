package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * @author davidLANG
 */
class IncremanteInstruction extends AbstractExecute {

    IncremanteInstruction() {
		super(Language.INCR);
	}

	/**
     * Execute the "incr" method of Memory Class
     *
     * @param memory Memory machine
     */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        argumentInstruction.getMemory().incr();
    }

}
