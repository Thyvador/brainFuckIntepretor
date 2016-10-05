package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * @author davidLANG
 */
class RightExecute implements InterpreterInterface {
    /**
     * Execute the "right" method of Memory Class
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) throws MemoryOutOfBoundsException {
        machine.right();
    }
}
