package net.brainfuck.exception;

public class BracketsParseException extends Exception {

	private static final long serialVersionUID = -4475769070470707772L;

	public BracketsParseException() {
		super("Bad parenthesized expression");
	}
	
	public BracketsParseException(String string) {
		super("Bad parenthesized expression at char " + string);
	}

}
