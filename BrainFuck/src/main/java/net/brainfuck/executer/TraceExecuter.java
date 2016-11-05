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

    @Override
    public void execute(InterpreterInterface i, Memory m, Reader r, BfImageWriter imageWriter, String fileName) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException {
        Logger.setWriter(fileName);
        i.trace(m,r);
    }
}
