package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * @author davidLANG
 */
class LeftExecute implements InterpreterInterface {
    /**
     * Execute the "left" method of Memory Class
     * @param memory Memory machine
     */
    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException {
        memory.left();
    }

    /**
     * Print the short syntax of "left" command
     */
    @Override
    public void rewrite() {
        System.out.println(Language.LEFT.getShortSyntax());
    }
}
