package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * @author davidLANG
 */
class DecremanteExecute extends AbstractExecute {
	
    DecremanteExecute() {
		super(Language.DECR);
	}

	/**
     * Execute "decr" method from class Memory
     *
     * @param memory Memory machine
     */
    @Override
    public void execute(Memory memory, Reader reader) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        memory.decr();
    }

}
