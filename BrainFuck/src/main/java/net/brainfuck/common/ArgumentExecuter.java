package net.brainfuck.common;

/**
 * @author davidLANG
 */
public class ArgumentExecuter extends ArgumentInstruction {
    private Memory memory;
    private Reader reader;
    private BfImageWriter imageWriter;

    public ArgumentExecuter(Memory memory, Reader reader, BfImageWriter bfImageWriter) {
        super(memory, reader);
        this.imageWriter = bfImageWriter;
    }

    public BfImageWriter getImageWriter() {
        return imageWriter;
    }
}

