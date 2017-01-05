package net.brainfuck.executer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.InstructionInterface;

import java.io.Writer;

public class GenerateExecuter implements ContextExecuter {

    private Writer writer;

    public GenerateExecuter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void execute(InstructionInterface i, Memory memory, Executable reader)
            throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn,
            BracketsParseException, SegmentationFaultException {
        try {
            writer.write(i.generate());
            writer.flush();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }

}
