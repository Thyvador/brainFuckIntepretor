package net.brainfuck;

import net.brainfuck.common.Initialyzer;

/**
 * The Class Main.
 * @author FooBar Team
 */
public class Main {

	/**
	 * Instantiates a new main.
	 *
	 * @param args the JVM args
	 */
	public Main(String[] args) {

		if (args.length == 0) {
			printUsage();
			System.exit(0);
		}
		new Initialyzer(args);

	}

	/**
	 * Print the usage.
	 */
	public static void printUsage() {
		System.out.println("Usage : bfck.sh -p FILE [--rewrite] [--translate] [--check] [-o output_file] [-i input_file]");
	}

	/**
	 * The main method.
	 *
	 * @param args command-line args
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			printUsage();
			System.exit(0);
		}
		new Initialyzer(args);
	}

}
