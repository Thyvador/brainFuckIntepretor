package net.brainfuck.common;

import net.brainfuck.interpreter.JumpTable;

/**
 * The Class ArgumentExecuter.
 *
 * @author davidLANG
 */
public class ArgumentExecuter extends ArgumentInstruction {
    private BfImageWriter imageWriter;

    /**
	 * Instantiates a new argument executer.
	 *
	 * @param memory
	 *            the memory
	 * @param reader
	 *            the reader
	 * @param bfImageWriter
	 *            the bf image writer
	 * @param jumpTable
	 *            the jump table
	 */
    public ArgumentExecuter(Memory memory, Reader reader, BfImageWriter bfImageWriter, JumpTable jumpTable) {
        super(memory, reader, jumpTable);
        this.imageWriter = bfImageWriter;
    }

    /**
	 * Gets the image writer.
	 *
	 * @return the image writer
	 */
    public BfImageWriter getImageWriter() {
        return imageWriter;
    }
}

