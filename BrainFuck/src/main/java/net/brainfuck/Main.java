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
import net.brainfuck.interpreter.Interpreter;

/**
 * @author user
 *
 */
public class Main {
	RuntimeMXBean runtimeMxBean;
	List<String> arguments;


	/**
	 * Print the usage
	 */
	private void printUsage() {
		System.out.println("Usage : bfck.sh -p FILE [--rewrite]");
	}

	/**
	 * Default constructor
	 * 
	 * @param args
	 */
	public Main(String[] args) {
		if (args.length == 0) {
			this.printUsage();
			System.exit(0);
		}
		try {
			runtimeMxBean = ManagementFactory.getRuntimeMXBean();
			arguments = runtimeMxBean.getInputArguments();
			ArgumentAnalyzer a = new ArgumentAnalyzer((String[]) arguments.toArray());
			if (a.getArgument(PATH) == null) {
				this.printUsage();
				System.exit(0);
			}
			Memory m = new Memory();
			Reader r = new BfReader(a.getArgument(PATH));
			Interpreter i = new Interpreter(m, r, a);
			i.interprate();
			System.out.println(m);
		} catch (IOException | SyntaxErrorException | FileNotFoundException | IncorrectArgumentException e) {
			// Exit code not set
			System.exit(4);
		} catch (MemoryOutOfBoundsException e) {
			System.exit(1);
		} catch (MemoryOverFlowException e) {
			System.exit(2);
		}
		System.exit(0);
	}

	/**
	 * @param args
	 *            command-line args
	 */
	public static void main(String[] args) {
		new Main(args);
	}

}