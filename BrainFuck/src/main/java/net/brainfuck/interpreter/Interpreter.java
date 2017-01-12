
package net.brainfuck.interpreter;

import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SegmentationFaultException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * The interpretor of brainfuck.
 * Execute the assiocate instruction to the syntax
 *
 * @author FooBar Team
 */

public class Interpreter {
	private Executer executer;
	private Executable reader;

	/**
	 * Constructor which initialize attribute.
	 *
	 * @param executer the executer
	 * @param reader the reader
	 * @throws FileNotFoundException throw by setIo()
	 * @throws IOException throw bt BfImageWriter
	 */
	public Interpreter(Executer executer, Executable reader) throws  IOException {
		this.executer = executer;
		this.reader = reader;
	}





	/**
	 * Interpret all characters which can be read with the attribute reader.
	 *
	 * @throws IOException {@link IOException} if reader throw an exception.
	 * @throws SyntaxErrorException {@link SyntaxErrorException} if an error of syntax is found.
	 * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException} if memory throw an exception.
	 * @throws MemoryOverFlowException throw by memory
	 * @throws FileNotFoundIn the file not found in
	 * @throws BracketsParseException throw by executer
	 * @throws FileNotFoundException the file not found exception
	 * @throws SegmentationFaultException 
	 */
	public void interprate() throws IOException, SyntaxErrorException, MemoryOutOfBoundsException,
			MemoryOverFlowException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
		AbstractInstruction instruction;

        while ((instruction = reader.getNext()) != null) {
            executer.execute(instruction, reader);
        }
        executer.end(reader);
    }


	/**
	 * Mark reader.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void markReader() throws IOException {
		reader.mark();
	}

	/**
	 * Reset reader.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BracketsParseException the brackets parse exception
	 */
	void resetReader() throws IOException, BracketsParseException {
		reader.reset();
	}

}

