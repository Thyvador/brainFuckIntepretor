package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * @author davidLANG
 */
class DecremanteExecute implements InterpreterInterface {

    /**
     * Execute "decr" method from class Memory
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        machine.decr();
    }
}
