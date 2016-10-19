package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * @author davidLANG
 */
class DecremanteExecute implements InterpreterInterface {
    /**
     * Execute "decr" method from class Memory
     *
     * @param memory Memory machine
     */
    @Override
    public void execute(Memory memory) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        memory.decr();
    }

    /**
     * Print the short syntax of "decremente" command
     */
    @Override
    public void rewrite() {
        System.out.println(Language.DECR.getShortSyntax());
    }
}
