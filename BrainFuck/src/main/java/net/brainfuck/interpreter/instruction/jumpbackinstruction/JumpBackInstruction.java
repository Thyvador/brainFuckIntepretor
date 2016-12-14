package net.brainfuck.interpreter.instruction.jumpbackinstruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Created by davidLANG on 14/12/2016.
 */
public abstract class JumpBackInstruction extends AbstractInstruction {
    protected JumpTable jumpTable;

    /**
     * Instantiates a new back instruction.
     */
    public JumpBackInstruction(Language language, JumpTable jumpTable) {
        super(language);
        this.jumpTable = jumpTable;
    }

    abstract public void execute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, IOException, BracketsParseException;
}
