package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 * @author davidLANG
 */
class RightExecute implements InterpreterInterface {
    /**
     * Execute the "right" method of Memory Class
     * @param memory Memory machine
     */
    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException {
        memory.right();
    }

    @Override
    public void rewrite() {
        System.out.println(Language.RIGHT.getShortSyntax());
    }
}
