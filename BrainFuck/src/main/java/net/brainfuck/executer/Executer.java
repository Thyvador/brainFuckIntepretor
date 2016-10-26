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
 * Created by davidLANG on 12/10/2016.
 */
public class Executer {
    private List<ContextExecuter> contextExecuters = new ArrayList<>();
    private Memory memory;
    private Reader reader;

    public Executer(Memory m, List<String> arguments, Reader r) {
        this.memory = m;
        this.reader = r;

        this.contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        for (String argument : arguments) {
            if (argument.equals(Context.UNCHECK.getSyntax())) {
                this.contextExecuters.remove(Context.contextMap.get(Context.UNCHECK.getSyntax()));
            } else {
                this.contextExecuters.add(Context.contextMap.get(argument));
            }
        }
    }


    public void execute(AbstractExecute i) throws MemoryOutOfBoundsException, BracketsParseException,
            MemoryOverFlowException, FileNotFoundIn, IOException {
        for (ContextExecuter c : contextExecuters) {
            c.execute(i, memory, reader);
        }
    }

}
