package net.brainfuck;

import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpretor.Interpretor;


public class Main {

	public Main(String filename) throws FileNotFoundException{

		try {
			Memory m = new Memory();
			Reader r = new BfReader(getClass().getResource(filename).getPath());
			Interpretor i = new Interpretor(m,r);
			i.interprate();
			System.out.println(m);
		} catch (IOException | SyntaxErrorException | FileNotFoundException e) {
			// Exit code not set
			System.exit(3);
		} catch (MemoryOutOfBoundsException e) {
			System.exit(1);
		} catch (MemoryOverFlowException e){
			System.exit(2);
		}
		System.exit(0);
	}

	private Main() throws FileNotFoundException {
		new Main("/assets/brainfuck/common/OutOfBoundLeft.bf"); // On lance sur un fichier au hasard
	}

	// prepare the executable jar
	public static void main(String[] args) throws FileNotFoundException {
        if(args.length == 2 && args[1].equals(("-p"))){
            new Main(args[1]);
        }else if(args.length == 0) {
            new Main(); // On lance sur un fichier au hasard
        }
	}

}
