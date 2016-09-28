package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * Created by davidLANG on 28/09/2016.
 */ // Concret class for right
class RightExecute implements InterpretorInterface {
    @Override
    public void execute(Memory machine) {
        try {
            machine.right();
        } catch (MemoryOutOfBoundsException e) {
            new MemoryOutOfBoundsException();
        }
    }
}
