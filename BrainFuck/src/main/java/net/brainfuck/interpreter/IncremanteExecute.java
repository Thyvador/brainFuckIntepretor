package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * @author davidLANG
 */
class IncremanteExecute extends AbstractExecute {

    IncremanteExecute() {
		super(Language.INCR);
	}

	/**
     * Execute the "incr" method of Memory Class
     *
     * @param memory Memory machine
     */
    @Override
    public void execute(Memory memory) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        memory.incr();
    }

}
