package net.brainfuck.interpreter.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.brainfuck.common.Logger;
import net.brainfuck.common.Pair;
import net.brainfuck.common.StringParser;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.ProcedureFunctionExecute;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.ContextExecuter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.io.Reader;

/**
 * The Class BfCompiler. Analyze the brainfuck syntax.
 */
public class BfCompiler {

	private Logger logger;
	private MacroInterpreter macroInterpreter;
	private List<AbstractInstruction> programme = new ArrayList<>();
	private Reader reader;
	private JumpTable jumpTable;
	private int pos = 0;


	/**
	 * Initialize the BfCompiler Object
	 *
	 * @param r Reader
	 * @param contextExecuters List of context get by ArgumentAnalyzer
	 * @throws FileNotFoundException when file is not found
	 * @throws IOException Throw by bufferedWriter
	 */
	public BfCompiler(Reader r, List<ContextExecuter> contextExecuters, Map<String, Macro> macros)
			throws FileNotFoundException, IOException {
		this(contextExecuters, macros);
		this.reader = r;
	}

	/**
	 * Initialize the BfCompiler Object
	 * @param contextExecuters List of context get by ArgumentAnalyzer
	 */
	public BfCompiler(List<ContextExecuter> contextExecuters, Map<String, Macro> macros) {
		this.logger = Logger.getInstance();
		this.macroInterpreter = new MacroInterpreter(macros);
		this.jumpTable = new JumpTable(!((contextExecuters.contains(Context.REWRITE.getContextExecuter())
				|| contextExecuters.contains(Context.TRANSLATE.getContextExecuter()))
				&& !contextExecuters.contains(Context.CHECK.getContextExecuter())));
	}


	/**
	 * Instantiates a new bf processing.
	 *
	 * @param reader the reader
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	public BfCompiler(Reader reader) throws IOException, FileNotFoundException {
		// TODO ce constructeur n'est plus utilisé mais il y a toujours une test l'utilisant
		this.reader = reader;
		jumpTable = new JumpTable(true);
	}


	/**
	 * Compile.
	 *
	 * @param contextExecuters the context executers
	 * @return the pair
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SyntaxErrorException the syntax error exception
	 * @throws BracketsParseException the brackets parse exception
	 */
	public Pair<List<AbstractInstruction>, JumpTable> compile(List<ContextExecuter> contextExecuters)
		throws IOException, SyntaxErrorException, BracketsParseException, java.io.IOException {
		writeAll();
		return endCompile(contextExecuters);
	}

	/**
	 * Compile.
	 *
	 * @param contextExecuters the context executers
	 * @return the pair
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SyntaxErrorException the syntax error exception
	 * @throws BracketsParseException the brackets parse exception
	 */
	public Pair<List<AbstractInstruction>, JumpTable> compile(List<ContextExecuter> contextExecuters, List<String> instructions)
			throws IOException, SyntaxErrorException, BracketsParseException, java.io.IOException {
		writeAll(instructions);
		return endCompile(contextExecuters);
	}

	/**
	 * End compile.
	 *
	 * @param contextExecuters the context executers
	 * @return the pair
	 * @throws BracketsParseException the brackets parse exception
	 */
	private Pair<List<AbstractInstruction>, JumpTable> endCompile(List<ContextExecuter> contextExecuters)
			throws BracketsParseException {
		if (contextExecuters.contains(Context.CHECK.getContextExecuter())) {
			jumpTable.finish();
		}
		return new Pair<>(programme, jumpTable);
	}


	private boolean isInstruction(String instruction) {
		return Language.instructionMap.containsKey(instruction);
	}

	private boolean isMacro(String str) {
		return macroInterpreter.contains(StringParser.splitSpace(str)[0].split("\\(")[0]);
	}

	/**
	 * Write all.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BracketsParseException the brackets parse exception
	 */
	private void writeAll() throws IOException, BracketsParseException, java.io.IOException, SyntaxErrorException {
		String instruction;

		while ((instruction = reader.getNext()) != null) {
			writeInstructionAndMacro(instruction);
			logger.incrInstruction();
		}
	}

	public void writeAll(List<String> instructions) throws SyntaxErrorException, IOException, BracketsParseException {

		for (String instruction : instructions) {
			writeInstructionAndMacro(instruction);
			logger.incrInstruction();
		}
	}

	/**
	 * Write instruction and macro.
	 *
	 * @param instruction the instruction
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BracketsParseException the brackets parse exception
	 */
	private void writeInstructionAndMacro(String instruction)
			throws IOException, BracketsParseException, SyntaxErrorException {

		if (isInstruction(instruction)) {
			writeInstruction(instruction);
		} else if (isMacro(instruction)) {
			writeMacro(instruction);
		} else if (instruction.matches("^[\\w\\d]+\\(.*\\).*")) {
			writeProcedureFunction(instruction);
		} else {
			throw new SyntaxErrorException(instruction);
		}
	}

	private void writeProcedureFunction(String instruction) throws SyntaxErrorException, IOException, BracketsParseException {
		String[] arguments = StringParser.getArguments(instruction);
		String name = instruction.substring(0, instruction.indexOf('('));


		if (!Language.instructionMap.containsKey(name)) {
			throw new SyntaxErrorException("Unknow function or procedure : " + name);
		}
		List<Integer> values = new ArrayList<>();
		if (arguments != null) {
			for (String argument : arguments) {
				if (!StringParser.isNumeric(argument))
					throw new SyntaxErrorException("bad argument " + argument);
				values.add(Integer.parseInt(argument));
			}
		}

		ProcedureFunctionExecute procedureFunctionExecute = new ProcedureFunctionExecute(
				values,
				(Executable)Language.instructionMap.get(name)
		);
		write(procedureFunctionExecute);
	}

	/**
	 * Write.
	 *
	 * @param currentInstruction the current instruction
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BracketsParseException the brackets parse exception
	 */
	private void write(AbstractInstruction currentInstruction) throws IOException, BracketsParseException {
		programme.add(currentInstruction);
		jumpTable.addInstruction(currentInstruction, ++pos);
	}


	private void writeMacro(String str) throws IOException, BracketsParseException, SyntaxErrorException {
		List<Language> instructions = macroInterpreter.writeMacro(str);

		for (Language instruction : instructions) {
			write(instruction.getInterpreter());
		}
	}

	private void writeInstruction(String str) throws IOException, BracketsParseException, SyntaxErrorException {
		AbstractInstruction currentInstruction = Language.instructionMap.get(str);
		if (currentInstruction instanceof Executable)
			throw new SyntaxErrorException("not parentheses to function " + str);
		write(currentInstruction);
	}

}
