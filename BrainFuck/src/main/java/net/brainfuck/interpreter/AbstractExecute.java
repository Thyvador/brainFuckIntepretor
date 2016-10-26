package net.brainfuck.interpreter;

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
    
}
