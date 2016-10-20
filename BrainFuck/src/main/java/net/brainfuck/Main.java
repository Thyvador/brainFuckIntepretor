package net.brainfuck;

import static net.brainfuck.common.ArgumentConstante.PATH;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.List;
import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.Interpreter;


public class Main {

	public Main(String[] args){
		try {
			ArgumentAnalyzer a = new ArgumentAnalyzer(args);
			Memory m = new Memory();
			Reader r = new BfReader(a.getArgument(PATH));
			Interpreter i = new Interpreter(m,r, a);
			i.interprate();
			System.out.println(m);
		} catch (IOException | SyntaxErrorException | FileNotFoundException | IncorrectArgumentException e) {
			System.exit(4);
		} catch (MemoryOutOfBoundsException e) {
			System.exit(1);
		} catch (MemoryOverFlowException e){
			System.exit(2);
		}
		System.exit(0);
	}


	private Main() {
//		String[] args = {"-p", "/assets/brainfuck/common/OutOfBoundLeft.bf"};
//		new Main(args); // On lance sur un fichier au hasard
	}

	static RuntimeMXBean runtimeMxBean;
	static List<String> arguments;
	// prepare the executable jar
	public static void main(String[] args) {
		System.out.println(args);
		new Main(args); // On lance sur un fichier au hasard
		//new Main(args);
	}

}
