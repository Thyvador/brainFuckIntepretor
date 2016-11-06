package net.brainfuck.common;

import net.brainfuck.exception.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Count and group informations about the execution, a.k.a :
 * PROG_SIZE : nombres d'instructions
 * EXEC_TIME : temps d'éxécution
 * EXEC_MOVE : nombre de changements du pointeur
 * DATA_MOVE : nombre de changement du pointeur de la memoire (<>)
 * DATA_WRITE : nombre d'écriture dans la mémoire (+-,)
 * DATA_READ : nombre de lecture memoire ( [].)
 *
 * @author Francois Melkonian
 *         on 05/11/2016
 */
public class Logger {
	private static int numInstructions = 0;
	private static int numExecMove = 0;
	private static int numMemoryWrite = 0;
	private static int numMemoryRead = 0;
	private static int numMemoryMove = 0;
	private static long start = 0;

	private static BufferedWriter writer = null;
	private static int step = 0;

	/**
	 * Create a string with all informations collected.
	 *
	 * @return informations
	 */
	public static String showResume() {
		String retour = "PROG_SIZE : " + numInstructions + "\n";
		retour += "EXEC_TIME : " + (System.currentTimeMillis() - start) + " ms\n";
		retour += "EXEC_MOVE : " + numExecMove + "\n";
		retour += "DATA_MOVE : " + numMemoryMove + "\n";
		retour += "DATA_WRITE : " + numMemoryWrite + "\n";
		retour += "DATA_READ : " + numMemoryRead + "\n";
		return retour;
	}

	/**
	 * Count the number of instruction read.
	 */
	public static void countInstruction() {
		numInstructions++;
	}

	/**
	 * Save the current timestamp of the start of the program
	 * Useful to calculate the exec time with a subtraction.
	 */
	public static void startExecTime() {
		start = System.currentTimeMillis();
	}

	/**
	 * Count the number of times that data is stored in memory.
	 */
	public static void countMemoryWrite() {
		numMemoryWrite++;
	}

	/**
	 * Count the number of times that data is read in memory.
	 */
	public static void countMemoryRead() {
		numMemoryRead++;
	}

	/**
	 * Count the number of times that the pointer move in memory.
	 */
	public static void countMemoryMove() {
		numMemoryMove++;
	}


	/**
	 * Increment by one the number of execution step.
	 */
	public static void incrStep() {
		Logger.step++;
	}

	/**
	 * Set the writer to write in the log file.
	 *
	 * @param fileName the programme path and name.
	 * @throws IOException {@link IOException} if the file cannot be access.
	 */
	public static void setWriter(String fileName) throws IOException {
		try {
			fileName = fileName.subSequence(0, fileName.lastIndexOf('.')) + ".log";
			Logger.writer = new BufferedWriter(new FileWriter(fileName));
		} catch (java.io.IOException e) {
			throw new IOException("Impossible to access : " + fileName + ".log");
		}
	}

	/**
	 * Write an instruction execution log into the log file.
	 *
	 * @param executionPointer the index of the execution pointer.
	 * @param memory           the memory.
	 * @throws IOException {@link IOException} if the file cannot be access.
	 */
	public static void write(int executionPointer, Memory memory) throws IOException {
		try {
			step += 1;
			writer.write(
					"============\n" +
							"Execution Step " + step +
							" : \nExecution pointer : " + executionPointer +
							". Data pointer  : C" + memory.getIndex() +
							".\nMemory : \n" + memory.toString() + "\n");
			writer.flush();
		} catch (java.io.IOException e) {
			throw new IOException("Impossible to write in the file.");
		}
	}

	/**
	 * Check if the writer was created.
	 *
	 * @return true if the writer is set, false otherwise.
	 */
	public static boolean isWriterOpen() {
		return writer != null;
	}

	/**
	 * Close the wrioter.
	 *
	 * @throws IOException {@link IOException} if the file cannot be access.
	 */
	public static void closeWriter() throws IOException {
		try {
			writer.close();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Count the char read in the input file
	 */
	public static void countMove() {
		numExecMove++;
	}
}
