package net.brainfuck.common;

import net.brainfuck.interpreter.JumpTable;

/**
 * The Class ArgumentInstruction.
 *
 * @author davidLANG
 */
public class ArgumentInstruction {
    private Memory memory;
    private Reader reader;
	private JumpTable jumpTable;

    /**
	 * Instantiates argumentAnalyzer new argument instruction.
	 *
	 * @param memory
	 *            the memory
	 * @param reader
	 *            the reader
	 * @param jumpTable
	 *            the jump table
	 */
    public ArgumentInstruction(Memory memory, Reader reader, JumpTable jumpTable) {
        this.memory = memory;
        this.reader = reader;
        this.jumpTable = jumpTable;
    }

    /**
	 * Gets the memory.
	 *
	 * @return the memory
	 */
    public Memory getMemory() {
        return memory;
    }

    /**
	 * Gets the reader.
	 *
	 * @return the reader
	 */
    public Reader getReader() {
        return reader;
    }
    
	/**
	 * Gets the jump table.
	 *
	 * @return the jumpTable
	 */
	public JumpTable getJumpTable() {
		return jumpTable;
	}

}
