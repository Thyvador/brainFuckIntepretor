
package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.BfImageWriter;
import net.brainfuck.common.Logger;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static net.brainfuck.common.ArgumentConstante.*;

/**
 * @author davidLANG
 *
 */

public class Interpreter {
	private Map<String, Language> interpretorExecuter = new HashMap<>();
    private Executer executer;
	private Reader reader;
    private ArgumentAnalyzer argumentAnalyzer;

    /**
     * Constructor which initialize attribute.
     * @param reader Reader
     * @param arg ArgumentAnalyzer use to get arguments
     * @throws FileNotFoundException throw by setIo()
     * @throws IOException throw bt BfImageWriter
     */
    public Interpreter(Reader reader, ArgumentAnalyzer arg, Executer executer) throws FileNotFoundException, IOException {
        this.reader = reader;
        this.argumentAnalyzer = arg;
        this.executer = executer;
        setIO();
        if(arg.getFlags().contains(Context.TRANSLATE.getSyntax())) {
	        executer.setImageWriter(new BfImageWriter());
        }
    }

	/**
	 * Set default Input and output files depending of args "-i" and "-o"
	 *
	 * @throws FileNotFoundException if the path entered isn't valide, the file is missing and can't be open
	 */
    private void setIO() throws FileNotFoundException{
        this.setIn();
        this.setOut();
    }

    /**
     * Set the default input to a files depending of args "-i"
     *
     * @throws FileNotFoundException throw by System.setIn()
     */
    private void setIn() throws FileNotFoundException {
        String inPath = argumentAnalyzer.getArgument(IN_PATH);
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
    private void setOut() throws FileNotFoundException {
        String outPath = argumentAnalyzer.getArgument(OUT_PATH);
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
     * @throws BracketsParseException throw by executer
     */
    public void interprate() throws IOException, SyntaxErrorException, MemoryOutOfBoundsException,
            MemoryOverFlowException, FileNotFoundIn, BracketsParseException, FileNotFoundException {
        String instruction;
        Language currentInstruction;

        while ((instruction = reader.getNext()) != null) {
            if ((currentInstruction = Language.languageMap.get(instruction)) == null) {
                throw new SyntaxErrorException(instruction);
            }
            executer.execute(currentInstruction.getInterpreter());
            Logger.countInstruction();
        }
        executer.end();
    }

    
    void markReader() throws IOException {
    	reader.mark();
    }
    
    void resetReader() throws IOException, BracketsParseException {
    	reader.reset();
    }
    
}

