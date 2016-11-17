package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

import java.io.IOException;

/**
 * @author François Melkonian
 *
 */
public class OutInstruction extends AbstractExecute {

    public OutInstruction() {
		super(Language.OUT);
	}

	/**
     * Print the value on current index, call "get" method from class Memory
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(ArgumentInstruction argumentInstruction) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        System.out.print( (char)argumentInstruction.getMemory().get());
    }

}
