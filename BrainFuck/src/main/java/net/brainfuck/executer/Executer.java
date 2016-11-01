package net.brainfuck.executer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.AbstractExecute;
import net.brainfuck.interpreter.InterpreterInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author davidLANG
 */
public class Executer {
    private List<ContextExecuter> contextExecuters = new ArrayList<>();
    private Memory memory;
    private Reader reader;

    /**
     * Initialize contextExecuters, memory and reader
     *
     * @param m Memory
     * @param arguments The long arguments
     * @param r Reader
     */
    public Executer(Memory m, List<String> arguments, Reader r) {
        this.memory = m;
        this.reader = r;

        // Initialize context executer
        this.contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        if (arguments.size() > 0) {
            this.contextExecuters.remove(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        }
        for (String argument : arguments) {
            this.contextExecuters.add(Context.contextMap.get(argument));
        }
    }

    /**
     * Execute the AbstractExecute command according to the context.
     *
     * @param i AbstractExecute command to execute
     * @throws MemoryOutOfBoundsException Throw by memory
     * @throws BracketsParseException Throw by Interpreter
     * @throws MemoryOverFlowException Throw by memory
     * @throws FileNotFoundIn Throw by reader
     * @throws IOException Throw by reader
     */
    public void execute(AbstractExecute i) throws MemoryOutOfBoundsException, BracketsParseException,
            MemoryOverFlowException, FileNotFoundIn, IOException {
        for (ContextExecuter c : contextExecuters) {
            c.execute(i, memory, reader);
        }
    }

    public void end() throws BracketsParseException {
        int index;
        if ((index = this.contextExecuters.indexOf(Context.contextMap.get(Context.CHECK.getSyntax()))) >= 0) {
            CheckExecuter checkExecuter = (CheckExecuter)this.contextExecuters.get(index);
            if (checkExecuter.getCpt() > 0) {
                throw new BracketsParseException();
            }
        }
    }


}
