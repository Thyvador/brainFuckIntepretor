package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;


/**
 *  Representation of Decrement instruction "-" "DECR".
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
	 * @param argumentExecuter the argument executer
	 * @throws MemoryOverFlowException the memory over flow exception
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 */
    @Override
    public void execute(ArgumentInstruction argumentExecuter) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        argumentExecuter.getMemory().decr();
    }

}
