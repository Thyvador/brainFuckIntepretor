package net.brainfuck.interpreter.instruction.jumpbackinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * @author FooBar Team
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

    public void setReader(Executable reader) {
        this.reader = reader;
    }

    @Override
	abstract public void execute(Memory memory) throws MemoryOutOfBoundsException, IOException, BracketsParseException;

    public Executable getExecutable() {
        return reader;
    }
}
