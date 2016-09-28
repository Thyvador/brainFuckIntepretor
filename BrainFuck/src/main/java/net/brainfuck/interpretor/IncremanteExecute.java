package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;

/**
 * Created by davidLANG on 28/09/2016.
 */ // Concret class for indent
class IncremanteExecute implements InterpretorInterface {
    @Override
    public void execute(Memory machine) {
        try {
            machine.incr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
