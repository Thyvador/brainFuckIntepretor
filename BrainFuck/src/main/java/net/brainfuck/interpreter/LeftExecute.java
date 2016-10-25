package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * @author davidLANG
 */
class LeftExecute extends AbstractExecute {
	
    LeftExecute() {
		super(Language.LEFT);
	}

	/**
     * Execute the "left" method of Memory Class
     * @param memory Memory machine
     */
    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException {
        memory.left();
    }

}
