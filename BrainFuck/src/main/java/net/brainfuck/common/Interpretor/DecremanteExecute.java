package net.brainfuck.common.Interpretor;

import net.brainfuck.common.Memory;

/**
 * Created by davidLANG on 28/09/2016.
 */ // Concret class for right
class DecremanteExecute implements InterpretorInterface {
    @Override
    public void execute(Memory machine) {
        try {
            machine.decr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
