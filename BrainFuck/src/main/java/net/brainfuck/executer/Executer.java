package net.brainfuck.executer;

import net.brainfuck.common.*;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.io.BfImageWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Executer.
 *
 * @author davidLANG
 */
public class Executer {
	private List<ContextExecuter> contextExecuters = new ArrayList<>();
	private Memory memory;
	private BfImageWriter bfImageWriter;
	private Logger logger;

	/**
	 * Initialize contextExecuters, memory and reader.
	 *
	 * @param argumentAnalyzer the argument analyzer
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException  the file not found exception
	 * @throws BracketsParseException the brackets parse exception
	 * @throws SyntaxErrorException   the syntax error exception
	 */
	public Executer(ArgumentAnalyzer argumentAnalyzer) throws IOException, FileNotFoundException, BracketsParseException, java.io.IOException, SyntaxErrorException {
		// Initialize context executer
		this.logger = Logger.getInstance();
		this.contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
		if (argumentAnalyzer.getFlags().size() > 0) {
			this.contextExecuters.remove(Context.contextMap.get(Context.UNCHECK.getSyntax()));
		}
		for (String argument : argumentAnalyzer.getFlags()) {
			this.contextExecuters.add(Context.contextMap.get(argument));
		}

	}

	/**
	 * Execute the AbstractInstruction command according to the context.
	 *
	 * @param instruction AbstractInstruction command to execute
	 * @throws MemoryOutOfBoundsException Throw by memory
	 * @throws BracketsParseException     Throw by Interpreter
	 * @throws MemoryOverFlowException    Throw by memory
	 * @throws FileNotFoundIn             Throw by reader
	 * @throws IOException                Throw by reader
	 * @throws SegmentationFaultException 
	 */
	public void execute(AbstractInstruction instruction, ExecutionReader reader) throws MemoryOutOfBoundsException, BracketsParseException,
			MemoryOverFlowException, FileNotFoundIn, IOException, SegmentationFaultException {
		for (ContextExecuter contextExecuter : contextExecuters) {
			contextExecuter.execute(instruction, memory, reader);

		}
	}



	/**
	 * This function must be called when all instruction have been read and execute
	 * She throw an error if the program has no enough parenthesis
	 * She close the Reader.*
	 * She close the imageWriter if the long argument "--translate" have been passed
	 *
	 *  @param reader reader to close
	 * @throws BracketsParseException throw if the program have more "[" than "]"
	 * @throws IOException            throw by reader.closeReader() and imageWrite.close()
	 * @throws FileNotFoundException  throw by reader.closeReader() and imageWrite.close()
	 */
	public void end(ExecutionReader reader) throws BracketsParseException, IOException, FileNotFoundException, MemoryOutOfBoundsException {
		reader.closeReader();

		int index;
		if ((index = this.contextExecuters.indexOf(Context.contextMap.get(Context.CHECK.getSyntax()))) >= 0) {
			CheckExecuter checkExecuter = (CheckExecuter) this.contextExecuters.get(index);
			if (checkExecuter.getCpt() > 0) {
				throw new BracketsParseException();
			}
		}
		if (this.contextExecuters.indexOf(Context.contextMap.get(Context.TRANSLATE.getSyntax())) >= 0) {
			bfImageWriter.close();
		}
		if (logger.isWriterOpen()) {
			logger.closeWriter();
		}
	}

	/**
	 * Gets the context executers.
	 *
	 * @return the context executers
	 */
	public List<ContextExecuter> getContextExecuters() {
		return contextExecuters;
	}

	/**
	 * Set the argument executer
	 * @param memory the memory
	 * @param bfImageWriter the image writer
	 */
	public void setArgumentExecuter(Memory memory, BfImageWriter bfImageWriter) {
		this.memory = memory;
		this.bfImageWriter = bfImageWriter;
	}
}
