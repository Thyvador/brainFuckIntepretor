package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;

/**
 * Created by davidLANG on 28/09/2016.
 */ // Concret class for right
class LeftExecute implements InterpretorInterface {
    @Override
    public void execute(Memory machine) {
        machine.left();
    }
}
