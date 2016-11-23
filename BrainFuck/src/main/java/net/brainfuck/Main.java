package net.brainfuck;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.Interpreter;
import net.brainfuck.interpreter.JumpTable;

import java.io.*;

import static net.brainfuck.common.ArgumentConstante.IN_PATH;
import static net.brainfuck.common.ArgumentConstante.OUT_PATH;
import static net.brainfuck.common.ArgumentConstante.PATH;


/**
 * The Class Main.
 */
public class Main {

    /**
	 * Print the usage.
	 */
    private void printUsage() {
        System.out.println("Usage : bfck.sh -p FILE [--rewrite] [--translate] [--check] [-o output_file] [-i input_file]");
    }


    /**
	 * Check path and exit if -p path isn't define
	 *
	 * @param a
	 *            the a
	 */
    private void checkPath(ArgumentAnalyzer a) {
        if (a.getArgument(PATH) == null) {
            this.printUsage();
            System.exit(0);
        }
    }

    /**
	 * Initialise the logger from context.
     * Useful when --trace args is used
	 *
	 * @param argAnalizer
	 *            the arg analizer
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    private void initLoggerFromContext(ArgumentAnalyzer argAnalizer) throws IOException {
        if (argAnalizer.getFlags().contains(Context.TRACE.getSyntax())){
            Logger.getInstance().setWriter(argAnalizer.getArgument(PATH));
        }
    }

    /**
	 * Inits the reader.
	 *
	 * @param argAnalizer
	 *            the arg analizer
	 * @return the reader
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
    private Reader initReader(ArgumentAnalyzer argAnalizer) throws FileNotFoundException {
        Reader r;
        if (argAnalizer.getArgument(PATH).endsWith(".bmp")) {
            r = new BfImageReader(argAnalizer.getArgument(PATH));
        } else {
            r = new BfReader(argAnalizer.getArgument(PATH));
        }
        return r;
    }
	/**
	 * Set the default input to a files depending of args "-i".
	 *
	 * @param argAnalizer
	 *            the new in
	 * @throws FileNotFoundException
	 *             throw by System.setIn()
	 */
    private void setIn(ArgumentAnalyzer argAnalizer) throws FileNotFoundException, IncorrectArgumentException {
        String inPath = argAnalizer.getArgument(IN_PATH);
        if(inPath != null){
            try {
                System.setIn(new FileInputStream(inPath));
            } catch (java.io.FileNotFoundException e) {
	            throw new IncorrectArgumentException("IN_PATH file '"+inPath+"' don't exist");

            }
        }
    }

    /**
	 * Set the default output to a files depending of args "-i".
	 *
	 * @param argAnalizer
	 *            the new out
	 * @throws FileNotFoundException
	 *             throw by System.setOut()
	 */
    private void setOut(ArgumentAnalyzer argAnalizer) throws FileNotFoundException, IncorrectArgumentException {
        String outPath = argAnalizer.getArgument(OUT_PATH);
        if(outPath != null){
	        File outFile = new File(outPath);
	        if(!outFile.exists()||outPath.startsWith("-")){
		        throw new IncorrectArgumentException("OUT_PATH file '"+outPath+"' don't exist");
	        }
            try {
                PrintStream printStream = new PrintStream(outPath);
                System.setOut(printStream);
            } catch (java.io.FileNotFoundException e) {
                throw new FileNotFoundException(outPath);
            }
        }
    }

    /**
	 * Set default Input and output files depending of args "-i" and "-o".
	 *
	 * @param a
	 *            the new io
	 * @throws FileNotFoundException
	 *             if the path entered isn't valide, the file is missing and can't be open
	 */
    private void setIO(ArgumentAnalyzer a) throws FileNotFoundException, IncorrectArgumentException {
        this.setIn(a);
        this.setOut(a);
    }

    /**
	 * Inits the.
	 *
	 * @param argAnalizer
	 *            the arg analizer
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SyntaxErrorException
	 *             the syntax error exception
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
    private void init(ArgumentAnalyzer argAnalizer) throws FileNotFoundException, IOException, SyntaxErrorException, BracketsParseException, java.io.IOException, IncorrectArgumentException {
        checkPath(argAnalizer);
        setIO(argAnalizer);
        initLoggerFromContext(argAnalizer);
    }


    /**
	 * Instantiates a new main.
	 *
	 * @param args
	 *            the args
	 */
    public Main(String[] args) {
        if (args.length == 0) {
            this.printUsage();
            System.exit(0);
        }
        try {
            ArgumentAnalyzer a = new ArgumentAnalyzer(args);

            this.init(a);
            Executer e = new Executer(a);
            Interpreter i = new Interpreter(e);

            Logger.getInstance().startExecTime();
            i.interprate();
            System.out.println(Logger.getInstance().showResume(e.getArgumentExecuter().getMemory()));
        } catch (IOException | SyntaxErrorException | FileNotFoundException | IncorrectArgumentException e) {
            // Exit code not set
            System.exit(5);
        } catch (MemoryOutOfBoundsException e) {
            System.exit(1);
        } catch (MemoryOverFlowException e) {
            System.exit(2);
        } catch (FileNotFoundIn e) {
            System.exit(3);
        } catch (BracketsParseException e) {
            System.exit(4);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
	 * The main method.
	 *
	 * @param args
	 *            command-line args
	 */
    public static void main(String[] args) {
        new Main(args);
    }

}
