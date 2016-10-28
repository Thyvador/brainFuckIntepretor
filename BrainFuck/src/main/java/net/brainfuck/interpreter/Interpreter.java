
package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.BfImageWriter;
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
	private BfImageWriter imgWrt;
    private ArgumentAnalyzer argumentAnalyzer;

    /**
     * Constructor which initialize attribute.
     * @param reader Reader
     * @param arg ArgumentAnalyzer use to get arguments
     * @throws IOException 
     */
    public Interpreter(Reader reader, ArgumentAnalyzer arg, Executer executer) throws FileNotFoundException, IOException {
        this.reader = reader;
        this.argumentAnalyzer = arg;
        this.executer = executer;

        if(arg.getFlags().contains(Context.TRANSLATE.getSyntax()) == true) {
        	String output = arg.getArgument(PATH).replace(".bf", ".bmp");
        	System.out.println(output);
	        imgWrt = new BfImageWriter(output);
        }
        setIO();
    }

	/**
	 * Set Input and output files depending of args "-i" and "-o"
	 *
	 * @throws FileNotFoundException if the path entered isn't valide, the file is missing and can't be open
	 */
    private void setIO() throws FileNotFoundException{
        String inPath = argumentAnalyzer.getArgument(IN_PATH);
        if(inPath != null){
            try {
                System.setIn(new FileInputStream(inPath));
            } catch (java.io.FileNotFoundException e) {
                throw new FileNotFoundException(inPath);
            }
        }
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
     * @throws BracketsParseException 
     */
    public void interprate() throws IOException, SyntaxErrorException, MemoryOutOfBoundsException,
            MemoryOverFlowException, FileNotFoundIn, BracketsParseException, FileNotFoundException {
        String instruction;
        AbstractExecute interpretor;
        
        while ((instruction = reader.getNext()) != null) {
            if (languageMap.get(instruction) == null || (interpretor = Language.languageMap.get(instruction).getInterpreter()) == null) {
                throw new SyntaxErrorException(instruction);
            }
            executer.execute(interpretor);
        }
        this.closeIO();
    }

    /**
     * Close reader and close imgWrt when the context need it
     *
     * @throws IOException could be throw by closing closing image writer
     * @throws BracketsParseException throw by reader.close()
     */
    private void closeIO() throws IOException, BracketsParseException, FileNotFoundException {
        reader.closeReader();
        if (this.argumentAnalyzer.getFlags().contains(Context.TRANSLATE.getSyntax())) {
            imgWrt.close();
        }
    }

    
    void markReader() throws IOException {
    	reader.mark();
    }
    
    void resetReader() throws IOException, BracketsParseException {
    	reader.reset();
    }
    
}

