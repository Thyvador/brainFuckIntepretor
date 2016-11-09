package net.brainfuck.common;

/**
 * @author davidLANG
 */
public class ArgumentInstruction {
    private Memory memory;
    private Reader reader;

    public ArgumentInstruction(Memory memory, Reader reader) {
        this.memory = memory;
        this.reader = reader;
    }

    public Memory getMemory() {
        return memory;
    }

    public Reader getReader() {
        return reader;
    }
}
