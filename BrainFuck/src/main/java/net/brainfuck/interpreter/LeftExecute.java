package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
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
     * @param
     */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException {
        argumentInstruction.getMemory().left();
    }

}
