package net.brainfuck.interpreter.instruction.jumpbackinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Created by davidLANG on 14/12/2016.
 */
public abstract class JumpBackInstruction extends AbstractInstruction {
    protected Executable reader;

    /**
     * Instantiates a new back instruction.
     */
    public JumpBackInstruction(Language language, Executable executionReader) {
        super(language);
        this.reader = executionReader;
    }

    abstract public void execute(Memory memory) throws MemoryOutOfBoundsException, IOException, BracketsParseException;
}
