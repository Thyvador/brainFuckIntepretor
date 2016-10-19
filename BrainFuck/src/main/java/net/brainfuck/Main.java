package net.brainfuck;

import static net.brainfuck.common.ArgumentConstante.PATH;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
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
			for (String z:arguments ) {
				System.out.println(z);
			}
			ArgumentAnalyzer a = new ArgumentAnalyzer(args);
			Memory m = new Memory();
			for (String z :
					args) {
				System.out.println(z);
			}
			System.out.println("get arg : "+args);
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
		runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		arguments = runtimeMxBean.getInputArguments();
		String[] s = new String[arguments.size()];
		for (int i = 0; i < arguments.size(); i++) {
			s[i]=arguments.get(i);
		}
		new Main(s); // On lance sur un fichier au hasard
		//new Main(args);
	}

}
