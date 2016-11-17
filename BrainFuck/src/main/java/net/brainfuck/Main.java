package net.brainfuck;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.BfCompiler;
import net.brainfuck.interpreter.Interpreter;
import net.brainfuck.interpreter.JumpTable;

import java.io.*;

import static net.brainfuck.common.ArgumentConstante.IN_PATH;
import static net.brainfuck.common.ArgumentConstante.OUT_PATH;
import static net.brainfuck.common.ArgumentConstante.PATH;


public class Main {

    /**
     * Print the usage
     */
    private void printUsage() {
        System.out.println("Usage : bfck.sh -p FILE [--rewrite] [--translate] [--check] [-o output_file] [-i input_file]");
    }

    private ArgumentExecuter initArgumentExecuter(ArgumentAnalyzer a, Memory m, Reader r, JumpTable jumpTable) throws IOException, FileNotFoundException {
        BfImageWriter bfImageWriter = null;

        if(a.getFlags().contains(Context.TRANSLATE.getSyntax())) {
            bfImageWriter = new BfImageWriter();
        }

        return new ArgumentExecuter(m, r, bfImageWriter, jumpTable);
    }

    private void checkPath(ArgumentAnalyzer a) {
        if (a.getArgument(PATH) == null) {
            this.printUsage();
            System.exit(0);
        }
    }

    private void initLoggerFromContext(ArgumentAnalyzer argAnalizer) throws IOException {
        if (argAnalizer.getFlags().contains(Context.TRACE.getSyntax())){
            Logger.setWriter(argAnalizer.getArgument(PATH));
        }
    }

    private Reader initReader(ArgumentAnalyzer argAnalizer) throws FileNotFoundException {
        Reader r;
        if (argAnalizer.getArgument(PATH).endsWith(".bmp")) {
            r = new BfImageReader(argAnalizer.getArgument(PATH));
        } else {
            r = new BfReader(argAnalizer.getArgument(PATH));
        }
        return r;
    }

    private JumpTable initJumpTable(ArgumentAnalyzer argAnalizer) throws FileNotFoundException, IOException, SyntaxErrorException, BracketsParseException {
    	JumpTable jumpTable = new JumpTable(initReader(argAnalizer));
    	return jumpTable;
	}

	/**
     * Set the default input to a files depending of args "-i"
     *
     * @throws FileNotFoundException throw by System.setIn()
     */
    private void setIn(ArgumentAnalyzer argAnalizer) throws FileNotFoundException {
        String inPath = argAnalizer.getArgument(IN_PATH);
        if(inPath != null){
            try {
                System.setIn(new FileInputStream(inPath));
            } catch (java.io.FileNotFoundException e) {
                throw new FileNotFoundException(inPath);
            }
        }
    }

    /**
     * Set the default output to a files depending of args "-i"
     *
     * @throws FileNotFoundException throw by System.setOut()
     */
    private void setOut(ArgumentAnalyzer argAnalizer) throws FileNotFoundException {
        String outPath = argAnalizer.getArgument(OUT_PATH);
        if(outPath != null){
            try {
                PrintStream printStream = new PrintStream(outPath);
                System.setOut(printStream);
            } catch (java.io.FileNotFoundException e) {
                throw new FileNotFoundException(outPath);
            }
        }
    }

    /**
     * Set default Input and output files depending of args "-i" and "-o"
     *
     * @throws FileNotFoundException if the path entered isn't valide, the file is missing and can't be open
     */
    private void setIO(ArgumentAnalyzer a) throws FileNotFoundException{
        this.setIn(a);
        this.setOut(a);
    }

    private ArgumentExecuter init(ArgumentAnalyzer argAnalizer) throws FileNotFoundException, IOException, SyntaxErrorException, BracketsParseException, java.io.IOException {
        checkPath(argAnalizer);
        setIO(argAnalizer);
        initLoggerFromContext(argAnalizer);

        Memory m = new Memory();
        /*Reader r = this.initReader(argAnalizer);
        JumpTable jumpTable = initJumpTable(argAnalizer);*/
        Pair <Reader, JumpTable> readerAndJump = new BfCompiler(initReader(argAnalizer)).compile();
        return initArgumentExecuter(argAnalizer, m, readerAndJump.getFirst(), readerAndJump.getSecond());
    }


    /**
     * Default constr uctor
     *
     * @param args
     */
    public Main(String[] args) {
        if (args.length == 0) {
            this.printUsage();
            System.exit(0);
        }
        try {
            ArgumentAnalyzer a = new ArgumentAnalyzer(args);

            ArgumentExecuter argumentExecuter = this.init(a);
            Executer e = new Executer(a.getFlags(), argumentExecuter);
            Interpreter i = new Interpreter(e, argumentExecuter);

            Logger.startExecTime();
            i.interprate();
            System.out.println(Logger.showResume(argumentExecuter.getMemory()));
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
     * @param args command-line args
     */
    public static void main(String[] args) {
        new Main(args);
    }

}
