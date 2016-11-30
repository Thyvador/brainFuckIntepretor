package net.brainfuck.executer;

import net.brainfuck.common.*;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.BfCompiler;
import net.brainfuck.interpreter.JumpTable;

import java.util.ArrayList;
import java.util.List;

import static net.brainfuck.common.ArgumentConstante.PATH;

/**
 * The Class Executer.
 *
 * @author davidLANG
 */
public class Executer {
    private List<ContextExecuter> contextExecuters = new ArrayList<>();

	public void setArgumentExecuter(ArgumentExecuter argumentExecuter) {
		this.argumentExecuter = argumentExecuter;
	}

	private ArgumentExecuter argumentExecuter;

    /**
	 * Initialize contextExecuters, memory and reader.
	 *
	 * @param argumentAnalyzer
	 *            the argument analyzer
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 * @throws SyntaxErrorException
	 *             the syntax error exception
	 */
    public Executer(ArgumentAnalyzer argumentAnalyzer,Reader preReader,Memory m) throws IOException, FileNotFoundException, BracketsParseException, java.io.IOException, SyntaxErrorException {

		// Initialize context executer
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
     * @param i AbstractInstruction command to execute
     * @throws MemoryOutOfBoundsException Throw by memory
     * @throws BracketsParseException     Throw by Interpreter
     * @throws MemoryOverFlowException    Throw by memory
     * @throws FileNotFoundIn             Throw by reader
     * @throws IOException                Throw by reader
     */
    public void execute(AbstractInstruction i) throws MemoryOutOfBoundsException, BracketsParseException,
            MemoryOverFlowException, FileNotFoundIn, IOException {
        for (ContextExecuter c : contextExecuters) {
            c.execute(i, argumentExecuter);
            //
        }
    }

    /**
     * This function must be called when all instruction have been read and execute
     * She throw an error if the program has no enough parenthesis
     * She close the Reader.*
     * She close the imageWriter if the long argument "--translate" have been passed
     *
     * @throws BracketsParseException throw if the program have more "[" than "]"
     * @throws IOException            throw by reader.closeReader() and imageWrite.close()
     * @throws FileNotFoundException  throw by reader.closeReader() and imageWrite.close()
     */
    public void end() throws BracketsParseException, IOException, FileNotFoundException {
        argumentExecuter.getReader().closeReader();

        int index;
        if ((index = this.contextExecuters.indexOf(Context.contextMap.get(Context.CHECK.getSyntax()))) >= 0) {
            CheckExecuter checkExecuter = (CheckExecuter) this.contextExecuters.get(index);
            if (checkExecuter.getCpt() > 0) {
                throw new BracketsParseException();
            }
        }
        if (this.contextExecuters.indexOf(Context.contextMap.get(Context.TRANSLATE.getSyntax())) >= 0) {
            argumentExecuter.getImageWriter().close();
        }
        if(Logger.getInstance().isWriterOpen()){
            Logger.getInstance().closeWriter();
        }
    }




	/**
	 * Gets the argument executer.
	 *
	 * @return the argument executer
	 */
	public ArgumentExecuter getArgumentExecuter() {
		return argumentExecuter;
	}

	/**
	 * Gets the context executers.
	 *
	 * @return the context executers
	 */
	public List<ContextExecuter> getContextExecuters() {
		return contextExecuters;
	}
}
