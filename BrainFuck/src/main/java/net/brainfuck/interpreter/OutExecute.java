package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

import java.io.IOException;

/**
 * @author François Melkonian
 *
 */
public class OutExecute implements InterpreterInterface {

    /**
     * Print the value on current index, call "get" method from class Memory
     *
     * @param machine Memory machine
     */
    @Override
    public void execute(Memory machine) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        System.out.print( (char)machine.get());
    }

    @Override
    public void rewrite() {
            System.out.println(Language.OUT.getShortSyntax());
    }
}
