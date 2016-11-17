package net.brainfuck.common;

import net.brainfuck.interpreter.JumpTable;

/**
 * @author davidLANG
 */
public class ArgumentInstruction {
    private Memory memory;
    private Reader reader;
	private JumpTable jumpTable;

    public ArgumentInstruction(Memory memory, Reader reader, JumpTable jumpTable) {
        this.memory = memory;
        this.reader = reader;
        this.jumpTable = jumpTable;
    }

    public Memory getMemory() {
        return memory;
    }

    public Reader getReader() {
        return reader;
    }
    
	/**
	 * @return the jumpTable
	 */
	public JumpTable getJumpTable() {
		return jumpTable;
	}

}
