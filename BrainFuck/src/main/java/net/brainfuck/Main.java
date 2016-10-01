package net.brainfuck;

import net.brainfuck.common.LineReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpretor.Interpretor;


public class Main {
	
	private Main(String filename) throws FileNotFoundException, java.io.FileNotFoundException {

		try {
			Memory m = Memory.getInstance();
			LineReader r = new LineReader(filename);
			Interpretor i = new Interpretor(m,r);
			i.interprate();
			System.out.println(m);
		} catch (IOException e) {
			System.exit(3);
		} catch (SynthaxeErrorException e) {
			System.exit(3);
		} catch (MemoryOutOfBoundsException e) {
			System.exit(1);
		} catch (MemoryOverFlowException e){
			System.exit(2);
		} catch (FileNotFoundException e){
			System.exit(3);
		}
		System.exit(0);
	}

	private Main() throws FileNotFoundException, java.io.FileNotFoundException {
		new Main("BrainFuck\\src\\main\\resources\\assets\\brainfuck\\common\\res.bf"); // On lance sur un fichier au hasard
	}

	// prepare the executable jar
	public static void main(String[] args) throws FileNotFoundException, java.io.FileNotFoundException {
        if(args.length == 2 && args[1].equals(("-p"))){
            new Main(args[1]);
        }else if(args.length == 0) {
            new Main(); // On lance sur un fichier au hasard
        }
	}
	
}
