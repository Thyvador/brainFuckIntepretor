package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

import java.io.IOException;

/**
 * @author François Melkonian
 *
 */
class OutExecute  extends AbstractExecute {

    OutExecute() {
		super(Language.OUT);
	}

	/**
     * Print the value on current index, call "get" method from class Memory
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine, Reader reader) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        System.out.print( (char)machine.get());
    }

}
