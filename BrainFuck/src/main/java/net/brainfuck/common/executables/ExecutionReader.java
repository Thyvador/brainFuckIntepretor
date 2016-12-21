package net.brainfuck.common.executables;

import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

import java.util.List;

/**
 * Created by Alexandre on 30/11/2016.
 */
public class ExecutionReader extends Executable {

    public ExecutionReader(List<Language> instructions, JumpTable jumpTable) {
        super(instructions, jumpTable);
    }



}

