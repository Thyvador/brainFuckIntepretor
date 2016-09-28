package net.brainfuck.common;

import net.brainfuck.common.exception.FileNotFoundException;

import java.io.IOException;

public class Main {
	
	private Main() throws FileNotFoundException, java.io.FileNotFoundException {
		System.out.println("penis");
		Memory m = Memory.getInstance();
		Reader r = new FileReader("C:\\Users\\user\\Desktop\\indianwomen.txt");
		Interpretor i = new Interpretor(m,r);

		try {
			i.interprate();
		} catch (net.brainfuck.common.exception.IOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
	
	public static void main(String[] args) throws FileNotFoundException, java.io.FileNotFoundException {
		new Main();
	}
	
}
