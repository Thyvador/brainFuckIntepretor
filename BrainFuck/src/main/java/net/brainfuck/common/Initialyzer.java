package net.brainfuck.common;

import net.brainfuck.Main;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.*;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.Interpreter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.processing.BfCompiler;
import net.brainfuck.interpreter.processing.BfPrecompiler;
import net.brainfuck.interpreter.processing.Macro;
import net.brainfuck.io.BfImageReader;
import net.brainfuck.io.BfImageWriter;
import net.brainfuck.io.BfReader;
import net.brainfuck.io.Reader;

import java.io.*;
import java.util.List;
import java.util.Map;

import static net.brainfuck.common.ArgumentConstante.PATH;

/**
 * @author Francois Melkonian
 */
public class Initialyzer {
    private Memory memory;
    private Reader reader;
    private Interpreter interpreter;
    private Executer executer;
    private OutputStream out;
    private InputStream in;

    private ArgumentAnalyzer argumentAnalyzer;

    /**
     * Initialise and run the clever code
     *
     * @param args the JVM args
     */
    public Initialyzer(String[] args) {
        try {
            argumentAnalyzer = new ArgumentAnalyzer(args);
            init(argumentAnalyzer);

            Logger.getInstance().startExecTime();
            interpreter.interprate();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            try {
                writer.write(Logger.getInstance().showResume(memory));
                writer.close();
            } catch (java.io.IOException e) {
                throw new IOException();
            }
            try {
                out.close();
                in.close();
            } catch (java.io.IOException e) {
                throw new IOException();
            }
        } catch (IOException | SyntaxErrorException | IncorrectArgumentException | SegmentationFaultException e) {
            // Exit code not set
            System.err.println(e.getMessage());
            System.exit(5);
        } catch (MemoryOutOfBoundsException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (MemoryOverFlowException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        } catch (FileNotFoundIn e) {
            System.err.println(e.getMessage());
            System.exit(3);
        } catch (BracketsParseException e) {
            System.err.println(e.getMessage());
            System.exit(4);
        } catch (Exception e) {
            System.err.println("An unknown error occured.");
            System.exit(6);
        }
        System.exit(0);

    }


    public InputStream getIn() throws FileNotFoundException {
        if (in != null)
            return in;
        String inputPath = null;
        try {
            inputPath = argumentAnalyzer.getArgument(ArgumentConstante.IN_PATH);
            if (inputPath == null)
                return in = System.in;

            return in = new FileInputStream(inputPath);

        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(inputPath);
        }
    }


    public OutputStream getOut() throws FileNotFoundException {
        if (out != null)
            return out;
        String outputPath = null;
        try {
            outputPath = argumentAnalyzer.getArgument(ArgumentConstante.OUT_PATH);
            if (outputPath == null)
                return out = System.out;

            return out = new FileOutputStream(new File(outputPath));
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(outputPath);
        }
    }


    private void analyzeArg() throws IOException {
        if (argumentAnalyzer.getArgument(PATH) == null) {
            Main.printUsage();
            System.exit(0);
        }
        initLoggerFromContext(argumentAnalyzer);
    }

    /**
     * Inits the.
     *
     * @param argumentAnalyzer
     * @throws FileNotFoundException  the file not found exception
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws SyntaxErrorException   the syntax error exception
     * @throws BracketsParseException the brackets parse exception
     */
    private void init(ArgumentAnalyzer argumentAnalyzer) throws FileNotFoundException, IncorrectArgumentException, IOException, SyntaxErrorException, BracketsParseException, MemoryOutOfBoundsException {
        analyzeArg();
        initReader();
        BfImageWriter bfImageWriter = null;
        if (argumentAnalyzer.getFlags().contains(Context.TRANSLATE.getSyntax())) {
            bfImageWriter = new BfImageWriter(getOut());
        }
        Context.setExecuter(bfImageWriter, new OutputStreamWriter(getOut()));
        try {
            executer = new Executer(argumentAnalyzer);
            Language.setInstructions(new InputStreamReader(getIn()), new OutputStreamWriter(getOut()));
            memory = new Memory();
            BfCompiler compiler = initCompiler(memory);
            Pair<List<AbstractInstruction>, JumpTable> readerAndJump = compiler.compile(executer.getContextExecuters());

            ExecutionReader executionReader = null;
            if (readerAndJump != null) {
                JumpTable jumpTable = readerAndJump.getSecond();
                List<AbstractInstruction> instructions = readerAndJump.getFirst();
                executionReader = new ExecutionReader(instructions, jumpTable);
                Language.setExecutable(executionReader);
            }

            if (argumentAnalyzer.getFlags().contains(Context.GENERATE.getSyntax())) {
                Writer wrt = new OutputStreamWriter(getOut());
                wrt.write("#include <stdio.h>\n\n");
                wrt.write("int memory[30000] = {};\n\n");
                for (Executable e : Executable.getExecutableRegistry()) {
                    wrt.write(e.generate());
                }
                wrt.close();
            }

            executer.setArgumentExecuter(memory, bfImageWriter);
            interpreter = new Interpreter(executer, executionReader);
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }

    private BfCompiler initCompiler(Memory memory) throws IOException, SyntaxErrorException, BracketsParseException, MemoryOutOfBoundsException {
        try {
            BfPrecompiler bfPrecompiler = new BfPrecompiler(reader);
            Map<String, Macro> macros = bfPrecompiler.
                    analyzeMacro();
            bfPrecompiler.analyzeProcedure(executer.getContextExecuters(), macros, memory);
            return new BfCompiler(reader, executer.getContextExecuters(), macros);
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }

    private void initReader() throws FileNotFoundException {
        if (argumentAnalyzer.getArgument(PATH).endsWith(".bmp")) {
            reader = new BfImageReader(argumentAnalyzer.getArgument(PATH));
        } else {
            reader = new BfReader(argumentAnalyzer.getArgument(PATH));
        }
    }

    /**
     * Initialise the logger from context.
     * Useful when --trace args is used
     *
     * @param argAnalizer the arg analizer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void initLoggerFromContext(ArgumentAnalyzer argAnalizer) throws IOException {
        if (argAnalizer.getFlags().contains(Context.TRACE.getSyntax())) {
            Logger.getInstance().setWriter(argAnalizer.getArgument(PATH));
        }
    }

}
