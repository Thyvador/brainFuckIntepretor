package net.brainfuck;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import net.brainfuck.common.BfReader;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.Interpreter;

import static net.brainfuck.ArgumentConstante.PATH;


public class Main {
	RuntimeMXBean runtimeMxBean;
	List<String> arguments;


	public Main(String[] args){

		try {
			runtimeMxBean = ManagementFactory.getRuntimeMXBean();
			arguments = runtimeMxBean.getInputArguments();
			ArgumentAnalyzer a = new ArgumentAnalyzer((String[]) arguments.toArray());
			Memory m = new Memory();
			Reader r = new BfReader(a.getArgument(PATH));

			Interpreter i = new Interpreter(m,r, a);
			i.interprate();
			System.out.println(m);
		} catch (IOException | SyntaxErrorException | FileNotFoundException | IncorrectArgumentException e) {
			// Exit code not set
			System.exit(4);
		} catch (MemoryOutOfBoundsException e) {
			System.exit(1);
		} catch (MemoryOverFlowException e){
			System.exit(2);
		} catch (FileNotFoundIn e) {
			System.exit(3);
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
		String[] args2 = {"-p", "Brainfuck/src/main/resources/assets/brainfuck/common/res.bf", "--rewrite","-i",};
		new Main(args2); // On lance sur un fichier au hasard
		//new Main(args);
	}

}
