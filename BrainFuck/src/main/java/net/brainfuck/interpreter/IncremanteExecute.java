package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * @author davidLANG
 */
class IncremanteExecute implements InterpreterInterface {

    /**
     * Execute the "incr" method of Memory Class
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        machine.incr();
    }
}
