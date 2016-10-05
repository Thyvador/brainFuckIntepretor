package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * @author davidLANG
 */
class LeftExecute implements InterpreterInterface {
    /**
     * Execute the "left" method of Memory Class
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) throws MemoryOutOfBoundsException {
        machine.left();
    }
}
