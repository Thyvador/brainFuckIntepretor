package net.brainfuck.interpreter;

abstract class AbstractExecute implements InterpreterInterface {

	private Language languageInstr;

	AbstractExecute(Language languageInstr) {
		this.languageInstr = languageInstr;
	}
	
	/**
     * Print the short syntax of the command wich implement this interface
     */
    final void rewrite() {
    	System.out.print(languageInstr.getShortSyntax());
    }
    
}
