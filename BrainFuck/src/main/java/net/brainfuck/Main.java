package net.brainfuck;

import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.Interpreter;

import static net.brainfuck.ArgumentConstante.IN;
import static net.brainfuck.ArgumentConstante.OUT;
import static net.brainfuck.ArgumentConstante.PATH;


public class Main {

	public Main(String[] args){

		try {
			ArgumentAnalizer a = new ArgumentAnalizer(args);
			Memory m = new Memory();
			Reader r = new BfReader(a.getArgument(PATH));

			Interpreter i = new Interpreter(m,r, a);
			i.interprate();
			System.out.println(m);
		} catch (IOException | SyntaxErrorException | FileNotFoundException e) {
			// Exit code not set
			System.exit(3);
		} catch (MemoryOutOfBoundsException e) {
			System.exit(1);
		} catch (MemoryOverFlowException e){
			System.exit(2);
		} catch (Exception e) {
			// Ajouter par argumentAnalizer il faut une nouvelle exception pour les arguments
			e.printStackTrace();
		}
		System.exit(0);
	}


	private Main() {
//		String[] args = {"-p", "/assets/brainfuck/common/OutOfBoundLeft.bf"};
//		new Main(args); // On lance sur un fichier au hasard
	}

	// prepare the executable jar
	public static void main(String[] args) {
		System.out.println("Working Directory = " +
				System.getProperty("user.dir"));
		String[] args2 = {"-p", "Brainfuck/src/main/resources/assets/brainfuck/common/res.bf", "--rewrite"};
		new Main(args2); // On lance sur un fichier au hasard
		//new Main(args);
	}

}
