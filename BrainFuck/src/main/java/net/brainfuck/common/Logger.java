package net.brainfuck.common;

import net.brainfuck.exception.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Count and group metrics about the execution
 *
 * @author Francois Melkonian,Alexandre Hiltcher
 */
public class Logger {

    private static Logger instance;

    private int numInstructions = 0;
    private int numExecMove = 0;
    private int numMemoryWrite = 0;
    private int numMemoryRead = 0;
    private int numMemoryMove = 0;
    private long start = 0;

    private BufferedWriter writer = null;
    private int step = 0;

    /**
     * Instantiates argumentAnalyzer new logger.
     */
    private Logger() {
        if (instance != null)
            throw new IllegalStateException("Already instantiated");
        startExecTime();
    }

    /**
     * Gets the single instance of World.
     *
     * @return the instance
     */
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Gets the num exec move.
     *
     * @return the num exec move
     */
    public int getNumExecMove() {
        return numExecMove;
    }


    /**
     * Gets the step.
     *
     * @return the step
     */
    public int getStep() {
        return step;
    }

    /**
     * Gets the writer.
     *
     * @return the writer
     */
    public BufferedWriter getWriter() {
        return writer;
    }

    /**
     * Set the writer to write in the log file.
     *
     * @param fileName the programme path and name.
     * @throws IOException {@link IOException} if the file cannot be access.
     */
    public void setWriter(String fileName) throws IOException {
        String logFileName = fileName.subSequence(0, fileName.lastIndexOf('.')) + ".log";
        try {
            writer = new BufferedWriter(new FileWriter(logFileName));
        } catch (java.io.IOException e) {
            throw new IOException("Impossible to access : " + logFileName);
        }
    }

    /**
     * Check if the writer was created.
     *
     * @return true if the writer is set, false otherwise.
     */
    public boolean isWriterOpen() {
        return writer != null;
    }

    /**
     * Create argumentAnalyzer string with all informations collected. Start with '\n' to stop
     *
     * @param m the m
     * @return informations
     */
    public String showResume(Memory m) {
        String retour = m + "PROG_SIZE : " + numInstructions + "\n";
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
    public void incrInstruction() {
        numInstructions++;
    }

    /**
     * Count the number of instruction read.
     */
    public void incrInstruction(int n) {
        numInstructions += n;
    }

    /**
     * Save the current timestamp of the start of the program
     * Useful to calculate the exec time with argumentAnalyzer subtraction.
     */
    public void startExecTime() {
        start = System.currentTimeMillis();
    }

    /**
     * Count the number of times that data is stored in memory.
     */
    public void countMemoryWrite() {
        numMemoryWrite++;
    }

    /**
     * Count the number of times that data is read in memory.
     */
    public void countMemoryRead() {
        numMemoryRead++;
    }

    /**
     * Count the number of times that the pointer move in memory.
     */
    public void countMemoryMove() {
        numMemoryMove++;
    }


    /**
     * Count the char read in the input file.
     */
    public void countMove() {
        numExecMove++;
    }

    /**
     * Increment by one the number of execution step.
     */
    public void incrStep() {
        step++;
    }


    /**
     * Write an instruction execution log into the log file.
     *
     * @param name
     * @param executionPointer the index of the execution pointer.
     * @param memory           the memory.
     * @throws IOException {@link IOException} if the file cannot be access.
     */
    public void write(String name, long executionPointer, Memory memory) throws IOException {
        try {
            step += 1;
            writer.write(
                    "============\n" +
                            "Execution Step " + step +
                            " : \nExecution pointer : " + executionPointer + " in : " + name +
                            ".\nData pointer  : C" + memory.getIndex() +
                            ".\nMemory : \n" + memory.toString() + "\n");
            writer.flush();
        } catch (java.io.IOException e) {
            throw new IOException("Impossible to write in the file.");
        }
    }

    /**
     * Write.
     *
     * @param message the message
     */
    public void write(String message) {
        try {
            writer.write(
                    "============\n" +
                            "Error: \n" + message);
            writer.flush();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(5);
        }
    }

    /**
     * Close the writer.
     *
     * @throws IOException {@link IOException} if the file cannot be access.
     */
    public void closeWriter() throws IOException {
        try {
            writer.close();
            writer = null;
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }

    /**
     * Override of the clone method because of the singleton.
     *
     * @return the object
     * @throws CloneNotSupportedException because this instance is argumentAnalyzer singleton
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone instance of this class");
    }

    /**
     * Reset the Logger
     */
    public void reset() {
        numExecMove = 0;
        numMemoryMove = 0;
        numInstructions = 0;
        numMemoryRead = 0;
        numMemoryWrite = 0;
        startExecTime();
    }
}
