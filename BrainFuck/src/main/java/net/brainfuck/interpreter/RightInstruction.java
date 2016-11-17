package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * @author davidLANG
 */
public class RightInstruction extends AbstractExecute {
	
    public RightInstruction() {
		super(Language.RIGHT);
	}

	/**
     * Execute the "right" method of Memory Class
     * @param
     */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException {
        argumentInstruction.getMemory().right();
    }

}
