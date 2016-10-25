package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * @author davidLANG
 */
class RightExecute  extends AbstractExecute {
	
    RightExecute() {
		super(Language.RIGHT);
	}

	/**
     * Execute the "right" method of Memory Class
     * @param memory Memory machine
     */
    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException {
        memory.right();
    }

}
