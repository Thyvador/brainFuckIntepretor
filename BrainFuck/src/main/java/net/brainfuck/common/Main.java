package net.brainfuck.common;

import net.brainfuck.common.exception.FileNotFoundException;

public class Main {
	
	private Main() throws FileNotFoundException, java.io.FileNotFoundException {
		System.out.println("penis");
		Memory m = Memory.getInstance();
		Reader r = new FileReader("C:\\Users\\user\\Desktop\\indianwomen.txt");
		Interpretor i = new Interpretor(m,r);
		System.exit(0);
	}
	
	public static void main(String[] args) throws FileNotFoundException, java.io.FileNotFoundException {
		new Main();
	}
	
}
