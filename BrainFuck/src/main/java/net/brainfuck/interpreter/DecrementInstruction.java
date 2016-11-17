package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentExecuter;
import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * @author davidLANG
 */
public class DecrementInstruction extends AbstractExecute {
	
    public DecrementInstruction() {
		super(Language.DECR);
	}

	/**
     * Execute "decr" method from class Memory
     *
     * @param memory Memory machine
     */
    @Override
    public void execute(ArgumentInstruction argumentExecuter) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        argumentExecuter.getMemory().decr();
    }

}
