package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Logger;
import net.brainfuck.exception.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractExecute.
 */
public abstract class AbstractExecute implements InstructionInterface {

	private Language languageInstr;

	/**
	 * Instantiates a new abstract execute.
	 *
	 * @param languageInstr
	 *            the language instr
	 */
	AbstractExecute(Language languageInstr) {
		this.languageInstr = languageInstr;
	}
	
	/**
	 * Print the short syntax of the command which implement this interface.
	 */
    public final void rewrite() {
    	System.out.print(languageInstr.getShortSyntax());
    }
    
    /* (non-Javadoc)
     * @see net.brainfuck.interpreter.InstructionInterface#translate()
     */
    public final String translate() {
    	return languageInstr.getColorSyntax();
    }

    /* (non-Javadoc)
     * @see net.brainfuck.interpreter.InstructionInterface#trace(net.brainfuck.common.ArgumentInstruction)
     */
    public final void trace(ArgumentInstruction argumentInstruction) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn {
		execute(argumentInstruction);
		Logger.getInstance().write(argumentInstruction.getReader().getExecutionPointer(), argumentInstruction.getMemory());
	}
    
}
