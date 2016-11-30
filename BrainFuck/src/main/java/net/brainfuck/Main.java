package net.brainfuck;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.Initialyzer;
import net.brainfuck.common.Logger;
import net.brainfuck.exception.*;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

import static net.brainfuck.common.ArgumentConstante.*;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * Print the usage.
	 */
	public static void printUsage() {
		System.out.println("Usage : bfck.sh -p FILE [--rewrite] [--translate] [--check] [-o output_file] [-i input_file]");
	}

	/**
	 * Instantiates a new main.
	 *
	 * @param args the JVM args
	 */
	public Main(String[] args) {

		if (args.length == 0) {
			this.printUsage();
			System.exit(0);
		}
		new Initialyzer(args);

	}

	/**
	 * The main method.
	 *
	 * @param args command-line args
	 */
	public void main(String[] args) {

		if (args.length == 0) {
			this.printUsage();
			System.exit(0);
		}
		new Initialyzer(args);
	}

}
