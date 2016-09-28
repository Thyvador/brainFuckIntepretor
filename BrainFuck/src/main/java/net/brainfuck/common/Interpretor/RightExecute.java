package net.brainfuck.common.Interpretor;

import net.brainfuck.common.Memory;

/**
 * Created by davidLANG on 28/09/2016.
 */ // Concret class for right
class RightExecute implements InterpretorInterface {
    @Override
    public void execute(Memory machine) {
        machine.right();
    }
}
