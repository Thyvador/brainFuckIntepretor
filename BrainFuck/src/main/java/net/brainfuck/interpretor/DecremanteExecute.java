package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;

/**
 * @author davidLANG
 */
class DecremanteExecute implements InterpretorInterface {
    /**
     * Execute "decr" method from class Memory
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) {
        try {
            machine.decr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
