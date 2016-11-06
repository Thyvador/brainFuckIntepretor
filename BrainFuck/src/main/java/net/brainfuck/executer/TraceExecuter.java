package net.brainfuck.executer;

import net.brainfuck.common.BfImageWriter;
import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.InterpreterInterface;

/**
 * Created by Alexandre on 05/11/2016.
 */
public class TraceExecuter implements ContextExecuter{

    /**
     * Execute the AbstractExecute command according to the "--trace" context.
     *
     * @param i the AbstractCommand to execute
     * @param m the memory representation
     * @param r the reader
     * @param imageWriter
     * @throws MemoryOverFlowException
     * @throws IOException
     * @throws MemoryOutOfBoundsException
     * @throws FileNotFoundIn
     * @throws BracketsParseException
     */
    @Override
    public void execute(InterpreterInterface i, Memory m, Reader r, BfImageWriter imageWriter) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException {
        i.trace(m,r);
    }
}
