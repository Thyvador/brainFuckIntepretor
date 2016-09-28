package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;

/**
 * @author davidLANG
 */
class IncremanteExecute implements InterpretorInterface {
    /**
     * Execute the "incr" method of Memory Class
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) {
        try {//J'aime les penis <3
            machine.incr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
