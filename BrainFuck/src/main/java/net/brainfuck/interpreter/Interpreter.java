
package net.brainfuck.interpreter;

import net.brainfuck.common.*;
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
	private ArgumentExecuter argumentExecuter;

	/**
	 * Constructor which initialize attribute.
	 * @throws FileNotFoundException throw by setIo()
	 * @throws IOException throw bt BfImageWriter
	 */
	public Interpreter(Executer executer, ArgumentExecuter argumentExecuter) throws FileNotFoundException, IOException {
		this.reader = argumentExecuter.getReader();
		this.executer = executer;
		this.argumentExecuter = argumentExecuter;
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
            System.out.println(currentInstruction);
            executer.execute(currentInstruction.getInterpreter());
            Logger.countMove();
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

