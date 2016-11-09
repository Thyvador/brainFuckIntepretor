package net.brainfuck.executer;

import net.brainfuck.common.ArgumentExecuter;
import net.brainfuck.common.BfImageWriter;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.InstructionInterface;

/**
 * @author Alexandre
 */
public class TraceExecuter implements ContextExecuter{

    /**
     * Execute the AbstractExecute command according to the "--trace" context.
     *
     * @param i the AbstractCommand to execute
     * @throws MemoryOverFlowException
     * @throws IOException
     * @throws MemoryOutOfBoundsException
     * @throws FileNotFoundIn
     * @throws BracketsParseException
     */
    @Override
    public void execute(InstructionInterface i, ArgumentExecuter argumentExecuter) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException {
        i.trace(argumentExecuter);
    }
}
