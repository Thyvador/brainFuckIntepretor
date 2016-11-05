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

    public static void countInstruction() {
        numInstructions++;
    }

    public static String showMetrics() {
        String retour = "PROG_SIZE : " + numInstructions + "\n";
        retour += "EXEC_TIME : " + (System.currentTimeMillis() - start) + " ms\n";
        retour += "DATA_MOVE : " + numMemoryMove + "\n";
        retour += "DATA_WRITE : " + numMemoryWrite + "\n";
        retour += "DATA_READ : " + numMemoryRead + "\n";
        return retour;
    }

    public static void startExecTime() {
        start = System.currentTimeMillis();
    }

    public static void countMemoryWrite() {
        numMemoryWrite++;
    }

    public static void countMemoryRead() {
        numMemoryRead++;
    }

    public static void countMemoryMove() {
        numMemoryMove++;
    }

    public static void countMove() {
        numExecMove++;
    }


    public static void incrStep() {
        Logger.step++;
    }

    public static void setWriter(String fileName) throws IOException {
        if (!isWriterOpen()) {
            try {
                fileName = fileName.subSequence(0, fileName.lastIndexOf('.')) + ".log";
                Logger.writer = new BufferedWriter(new FileWriter(fileName));
            } catch (java.io.IOException e) {
                throw new IOException("Impossible to access : " + fileName + ".log");
            }
        }
    }

    public static void write(int executionPointer, Memory memory) throws IOException {
        try {
            step += 1;
            writer.write(
                    "============\n"+
                    "Execution Step : " + step +
                    " : \nExecution pointer : " + executionPointer +
                    ". Data pointer  : " + memory.getIndex() +
                    ".\nMemory : \n" + memory.toString() + "\n");
            writer.flush();
        } catch (java.io.IOException e) {
            throw new IOException("Impossible to write in the file.");
        }
    }

    public static boolean isWriterOpen() {
        return writer != null;
    }

    public static void closeWriter() throws IOException {
        try {
            writer.close();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }
}
