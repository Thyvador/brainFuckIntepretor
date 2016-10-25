
package net.brainfuck.interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.ArgumentConstante;
import net.brainfuck.common.BfImageWriter;
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
    private Map<String, Language> interpretorExecuter = new HashMap<>();
    private Memory memory;
    private Reader reader;
    private boolean[] flags;
    private BfImageWriter imgWrt;

    /**
     * Constructor which initialize attribute.
     * @param memory Memory
     * @param reader Reader
     * @param arg ArgumentAnalyzer use to get arguments
     * @throws IOException 
     */
    public Interpreter(Memory memory, Reader reader, ArgumentAnalyzer arg) throws FileNotFoundException, IOException {
        this.reader = reader;
        this.memory = memory;
        this.flags = arg.getFlags();
        this.initLanguages();
        
        if(flags[ArgumentConstante.TRANSLATE]) {
        	String output = arg.getArgument(PATH).replace(".bf", ".bmp");
        	System.out.println(output);
        	try {
				imgWrt = new BfImageWriter(output);
			} catch (java.io.IOException e) {
				throw new IOException();
			}
        }
        
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
     * @throws BracketsParseException 
     */
    public void interprate() throws IOException, SyntaxErrorException , MemoryOutOfBoundsException, MemoryOverFlowException,FileNotFoundIn, BracketsParseException {
        String instruction;
        AbstractExecute interpretor;
        boolean execution = true;
        
        while ((instruction = reader.getNext()) != null) {
            if ((interpretor = this.interpretorExecuter.get(instruction).getInterpreter()) == null) {
                throw new SyntaxErrorException(instruction);
            }
            if (flags[ArgumentConstante.CHECK]) {
                execution = false;
            }
            if (flags[ArgumentConstante.REWRITE]) {
                interpretor.rewrite();
                execution = false;
            }
            if (flags[ArgumentConstante.TRANSLATE]) {
                try {
					imgWrt.write(interpretor.translate());
				} catch (java.io.IOException e) {
					throw new IOException();
				}
                execution = false;
            }
            if (execution) {
            	interpretor.execute(memory, reader);
            }
        }
        reader.close();
        if (flags[ArgumentConstante.TRANSLATE]) {
        	try {
				imgWrt.close();
			} catch (java.io.IOException e) {
				throw new IOException();
			}
        }
    }

    /**
     * Initialize the Hashmap which contains
     * class which implements InterpreterInterface associate with  syntaxe
     * for example RightExecute is associate with >
     */
    private void initLanguages() {
        Language[] languages = Language.values();
        for (Language language : languages) {
        	//AbstractExecute interpreter = language.getInterpreter();
            String[] aliases = language.getAliases();
            for (String alias : aliases) {
                this.interpretorExecuter.put(alias, language);
            }
        }
    }
    
    void markReader() throws IOException {
    	reader.mark();
    }
    
    void resetReader() throws IOException, BracketsParseException {
    	reader.reset();
    }
    
}

