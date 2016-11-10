package net.brainfuck.common;

import net.brainfuck.interpreter.JumpTable;

/**
 * @author davidLANG
 */
public class ArgumentExecuter extends ArgumentInstruction {
    private Memory memory;
    private Reader reader;
    private BfImageWriter imageWriter;

    public ArgumentExecuter(Memory memory, Reader reader, BfImageWriter bfImageWriter, JumpTable jumpTable) {
        super(memory, reader, jumpTable);
        this.imageWriter = bfImageWriter;
    }

    public BfImageWriter getImageWriter() {
        return imageWriter;
    }
}

