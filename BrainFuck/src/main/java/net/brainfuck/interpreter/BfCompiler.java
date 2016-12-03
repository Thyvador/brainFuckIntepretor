package net.brainfuck.interpreter;

import net.brainfuck.common.BfReader;
import net.brainfuck.common.Logger;
import net.brainfuck.common.Pair;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.ContextExecuter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class BfCompiler. Compile and analyze the brainfuck syntax.
 */
public class BfCompiler {

	List<Language> programme = new ArrayList<>();
	private Reader reader;
	private Writer writer;
	private File tmpFile;
	private JumpTable jumpTable;
	private int pos = 0;
	private Map<String, List<Language>> macros = new HashMap<>();

	/**
	 * Instantiates a new bf compiler.
	 *
	 * @param reader
	 *            the reader
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public BfCompiler(Reader reader) throws IOException, FileNotFoundException {

		this.reader = reader;
		jumpTable = new JumpTable(true);
		try {
			tmpFile = new File("compiledBrainFuck.o");
			tmpFile.deleteOnExit();
			writer = new BufferedWriter(new FileWriter(tmpFile));
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Initialize the BfCompiler Object
	 *
	 * @param r
	 *            Reader
	 * @param contextExecuters
	 *            List of context get by ArgumentAnalyzer
	 * @throws FileNotFoundException
	 *             when file is not found
	 * @throws IOException
	 *             Throw by bufferedWriter
	 */
	public BfCompiler(Reader r, List<ContextExecuter> contextExecuters) throws FileNotFoundException, IOException {

		this.reader = r;
		jumpTable = new JumpTable(!((contextExecuters.contains(Context.REWRITE.getContextExecuter())
				|| contextExecuters.contains(Context.TRANSLATE.getContextExecuter()))
				&& !contextExecuters.contains(Context.CHECK.getContextExecuter())));
		try {
			tmpFile = new File("compiledBrainFuck.o");
			tmpFile.deleteOnExit();
			writer = new BufferedWriter(new FileWriter(tmpFile));
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Get list of instruction of a macro.
	 *
	 * @param definitions
	 *            definition of a macro "++++"
	 * @param nb
	 *            the number of time the macro is call
	 * @return List of instruction : N * List of intruction of a macro
	 */
	private List<Language> getMacroInstructions(String definitions, int nb) {
		List<Language> instrList = new ArrayList<>();

		for (int cpt = 0; cpt < nb; cpt++) {
			instrList.addAll(macros.get(definitions));
		}
		return instrList;
	}

	/**
	 * Read a string and return the list of instruction corresponding
	 *
	 * @param definitions
	 *            definitions definition of a macro "++++"
	 * @return List of instruction
	 * @throws SyntaxErrorException
	 *             when an instruction is unknown
	 */
	private List<Language> getListOfInstruction(String definitions) throws SyntaxErrorException {
		Language language;
		List<Language> instrList = new ArrayList<>();

		if ((language = Language.languageMap.get(definitions)) != null) {
			instrList.add(language);
		} else {
			for (char inst : definitions.toCharArray()) {
				if ((language = Language.languageMap.get("" + inst)) == null) {
					throw new SyntaxErrorException("Bad macro definition : " + definitions);
				}
				instrList.add(language);
			}
		}
		return instrList;
	}

	/**
	 * Checks if is numeric.
	 *
	 * @param str
	 *            the string to analyze
	 * @return true, if is numeric
	 */
	private boolean isNumeric(String str) {
		return str.matches("^\\d+$");
	}

	/**
	 * Save a macro.
	 *
	 * @param instruction
	 *            the instruction
	 * @throws SyntaxErrorException
	 *             the syntax error exception
	 */
	private void saveMacro(String instruction) throws SyntaxErrorException {
		instruction = instruction.substring(1);
		String[] definitions = instruction.split("\\s+");
		List<Language> instrList = new ArrayList<>();

		int length = definitions.length;
		for (int i = 1; i < length; i++) {
			if (macros.containsKey(definitions[i])) {
				if (i + 1 < length && this.isNumeric(definitions[i + 1])) {
					int nb = Integer.parseUnsignedInt(definitions[i + 1]);
					instrList.addAll(getMacroInstructions(definitions[i++], nb));
				} else {
					instrList.addAll(getMacroInstructions(definitions[i], 1));
				}
			} else {
				instrList.addAll(getListOfInstruction(definitions[i]));
			}
		}
		macros.put(definitions[0], instrList);
	}

	/**
	 * Write macro.
	 *
	 * @param instruction
	 *            the instruction
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
	private boolean writeMacro(String instruction) throws IOException, BracketsParseException, SyntaxErrorException {
		String[] definitions = instruction.split("\\s+");

		if (macros.containsKey(definitions[0])) {
			int length = definitions.length;
			if (length > 2) {
				throw new SyntaxErrorException("Syntax behind macro '" + definitions[0] + "'");
			}

			int number = (length == 2 && this.isNumeric(definitions[1])) ? Integer.parseUnsignedInt(definitions[1]) : 1;
			for (int i = 0; i < number; i++) {
				for (Language instr : macros.get(definitions[0]))
					write(instr);
			}
			return true;
		}
		return false;
	}

	/**
	 * Write instruction and macro.
	 *
	 * @param instruction
	 *            the instruction
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
	private void writeInstructionAndMacro(String instruction)
			throws IOException, BracketsParseException, SyntaxErrorException {
		Language currentInstruction;

		if ((currentInstruction = Language.languageMap.get(instruction)) != null) {
			write(currentInstruction);
		} else if (macros.containsKey(instruction.split("\\s+")[0])) {
			writeMacro(instruction);
		} else {
			throw new SyntaxErrorException(instruction);
		}
	}

	/**
	 * Write all.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
	private void writeAll() throws IOException, BracketsParseException, java.io.IOException, SyntaxErrorException {
		String instruction;

		while ((instruction = reader.getNext()) != null) {
			writeInstructionAndMacro(instruction);
			Logger.getInstance().incrInstruction();
		}
	}

	/**
	 * Analyze macro.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 * @throws SyntaxErrorException
	 *             the syntax error exception
	 */
	private void analyzeMacro() throws java.io.IOException, IOException, BracketsParseException, SyntaxErrorException {
		boolean endofCompile = false;
		String instruction;

		while (!endofCompile && (instruction = ((BfReader) reader).getNextMacro()) != null) {
			if (Language.languageMap.get(instruction) == null && instruction.charAt(0) == BfReader.PREPROCESSING) {
				this.saveMacro(instruction);
			} else {
				writeInstructionAndMacro(instruction);
				endofCompile = true;
			}
			Logger.getInstance().incrInstruction();
		}
	}

	/**
	 * Compile.
	 *
	 * @param contextExecuters
	 *            the context executers
	 * @return the pair
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SyntaxErrorException
	 *             the syntax error exception
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
	public Pair<List<Language>, JumpTable> compile(List<ContextExecuter> contextExecuters)
			throws IOException, SyntaxErrorException, BracketsParseException, java.io.IOException {
		analyzeMacro();
		writeAll();
		return endCompile(contextExecuters);
	}

	/**
	 * End compile.
	 *
	 * @param contextExecuters
	 *            the context executers
	 * @return the pair
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Pair<List<Language>, JumpTable> endCompile(List<ContextExecuter> contextExecuters)
			throws BracketsParseException, IOException {
		if (contextExecuters.contains(Context.CHECK.getContextExecuter())) {
			System.out.println(contextExecuters);
			jumpTable.finish();
		}
		try {
			writer.close();
		} catch (java.io.IOException e1) {
			throw new IOException();
		}
		return new Pair<>(programme, jumpTable);
	}

	/**
	 * Write.
	 *
	 * @param currentInstruction
	 *            the current instruction
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
	private void write(Language currentInstruction) throws IOException, BracketsParseException {
		programme.add(currentInstruction);
		jumpTable.addInstruction(currentInstruction, ++pos);
	}

}
