
package net.brainfuck.interpreter;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.ArgumentConstante;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;

import static net.brainfuck.common.ArgumentConstante.*;
import static net.brainfuck.interpreter.Language.*;

/**
 * @author davidLANG
 *
 */

public class  Interpreter {
    private Map<String, InterpreterInterface> interpretorExecuter = new HashMap<>();
    private Memory memory;
    private Reader reader;
    private boolean[] flags;

    /**
     * Constructor which initialize attribute.
     * @param memory Memory
     * @param reader Reader
     * @param arg ArgumentAnalyzer use to get arguments
     */
    public Interpreter(Memory memory, Reader reader, ArgumentAnalyzer arg) throws FileNotFoundException {
        this.reader = reader;
        this.memory = memory;
        this.flags = arg.getFlags();
        this.initLanguages();
        setIO(arg);
    }


    private void setIO(ArgumentAnalyzer arg) throws FileNotFoundException{
        String inPath = arg.getArgument(IN_PATH);
        if(inPath != null){
            try {
                System.setIn(new FileInputStream(inPath));
            } catch (java.io.FileNotFoundException e) {
                throw new FileNotFoundException(inPath);
            }
        }
        String outPath = arg.getArgument(OUT_PATH);
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
     * Interpret all characters which can be read with the attribute reader.
     *
     * @throws SyntaxErrorException {@link SyntaxErrorException} if an error of syntax is found.
     * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException} if memory throw an exception.
     * @throws IOException {@link IOException}  if reader throw an exception.
     * @throws MemoryOverFlowException throw by memory
     */
    public void interprate() throws IOException, SyntaxErrorException , MemoryOutOfBoundsException, MemoryOverFlowException,FileNotFoundIn {
        String instruction;
        InterpreterInterface interpretor;

        while ((instruction = reader.getNext()) != null) {
            if ((interpretor = this.interpretorExecuter.get(instruction)) == null) {
                throw new SyntaxErrorException(instruction);
            }
            if (!flags[ArgumentConstante.CHECK]) {
                interpretor.execute(memory);
            }
            if (flags[ArgumentConstante.REWRITE]) {
                interpretor.rewrite();
            }
        }
        reader.close();
    }

    /**
     * Initialize the Hashmap which contains
     * class which implements InterpreterInterface associate with  syntaxe
     * for example RightExecute is associate with >
     */
    private void initLanguages() {
        Language[] languages = Language.values();
        for (Language language : languages) {
            InterpreterInterface interpreter = language.getInterpreter();
            String[] aliases = language.getAliases();
            for (String aliase : aliases) {
                this.interpretorExecuter.put(aliase, interpreter);
            }
        }
    }
    
}

