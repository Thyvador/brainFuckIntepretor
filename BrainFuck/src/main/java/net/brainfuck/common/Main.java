package net.brainfuck.common;

import java.io.IOException;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.interpretor.Interpretor;

public class Main {
	
	private Main(String filename) throws FileNotFoundException, java.io.FileNotFoundException {
		Memory m = Memory.getInstance();
		Reader r = new LineReader(filename);
		Interpretor i = new Interpretor(m,r);

		try {
			i.interprate();
		} catch (net.brainfuck.exception.IOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println(m);
        System.exit(0);
	}
	// prepare the executable jar
	public static void main(String[] args) throws FileNotFoundException, java.io.FileNotFoundException {
        if(args.length == 2 && args[1].equals(("-p"))){
            new Main(args[1]);
        }else if(args.length == 0) {
            new Main("C:\\Users\\user\\Desktop\\a.bf"); // On lance sur un fichier au hasard
        }
	}
	
}
