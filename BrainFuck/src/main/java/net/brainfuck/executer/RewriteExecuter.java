package net.brainfuck.executer;

import net.brainfuck.common.BfImageWriter;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.AbstractExecute;
import net.brainfuck.interpreter.InterpreterInterface;

/**
 * @author davidLANG
 */
class RewriteExecuter implements ContextExecuter {


    /**
     * Execute the AbstractExecute command according to the "--rewrite" context.
     *
     * @param i the AbstractCommand to execute
     * @param m the memory representation
     * @param r the reader
     * @throws MemoryOverFlowException throw by memory
     * @throws IOException throw by reader
     * @throws MemoryOutOfBoundsException throw by memory
     */
    @Override
    public void execute(InterpreterInterface i, Memory m, Reader r, BfImageWriter imageWriter) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException {
        i.rewrite();
    }
}
