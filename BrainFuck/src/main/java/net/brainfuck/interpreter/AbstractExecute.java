package net.brainfuck.interpreter;

abstract class AbstractExecute implements InterpreterInterface {

	private Language languageInstr;

	AbstractExecute(Language languageInstr) {
		this.languageInstr = languageInstr;
	}
	
	/**
     * Print the short syntax of the command which implement this interface
     */
    final void rewrite() {
    	System.out.print(languageInstr.getShortSyntax());
    }
    
    final String translate() {
    	return languageInstr.getColorSyntax();
    }
    
}
