package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Logger;
import net.brainfuck.exception.*;

/**
 * Abstraction of a
 */
public abstract class AbstractInstruction implements InstructionInterface {

	private Language languageInstr;

	/**
	 * Instantiates a new abstract execute.
	 *
	 * @param languageInstr
	 *            the language instr
	 */
	AbstractInstruction(Language languageInstr) {
		this.languageInstr = languageInstr;
	}
	
	/**
	 * Print the short syntax of the command which implement this interface.
	 */
    public final void rewrite() {
    	System.out.print(languageInstr.getShortSyntax());
    }

	/**
	 * Return the color (in hexa) which represent the instruction
	 * @return String hexa wich represent the color of the current instruction
	 */
	public final String translate() {
    	return languageInstr.getColorSyntax();
    }

	/**
	 *
	 * @param argumentInstruction
	 *            the argument instruction
	 * @throws IOException
	 * @throws MemoryOutOfBoundsException
	 * @throws BracketsParseException
	 * @throws MemoryOverFlowException
	 * @throws FileNotFoundIn
	 */
    public final void trace(ArgumentInstruction argumentInstruction) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn {
		execute(argumentInstruction);
		Logger.getInstance().write(argumentInstruction.getReader().getExecutionPointer(), argumentInstruction.getMemory());
	}
    
}
