package net.brainfuck.interpreter;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;

public abstract class AbstractExecute implements InterpreterInterface {

	private Language languageInstr;

	AbstractExecute(Language languageInstr) {
		this.languageInstr = languageInstr;
	}
	
	/**
     * Print the short syntax of the command which implement this interface
     */
    public final void rewrite() {
    	System.out.print(languageInstr.getShortSyntax());
    }
    
    public final String translate() {
    	return languageInstr.getColorSyntax();
    }

    public final void trace(Memory memory, Reader reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn {
		execute(memory,reader);
		Logger.write(reader.getExecutionPointer(), memory);
	}
    
}
