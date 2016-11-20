package net.brainfuck.interpreter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

// TODO: Auto-generated Javadoc
/**
 * The Class BfCompiler.
 */
public class BfCompiler {

	private Reader reader;
	private Writer writer;
	private File tmpFile;
	private JumpTable jumpTable;
	private int pos = 0;
	private Map<String, List<Language>> macros = new HashMap<>();

	//private Map<String, String>

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
		jumpTable = new JumpTable();
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
	 * Save simple macro.
	 */
	private void saveSimpleMacro(){

	}

	/**
	 * Save argument macro.
	 */
	private void saveArgumentMacro() {

	}

	/**
	 * Adds the macro to macro.
	 */
	private void addMacroToMacro() {

	}

	/**
	 * Checks if is numeric.
	 *
	 * @param str
	 *            the str
	 * @return true, if is numeric
	 */
	private boolean isNumeric(String str)
	{
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}

	/**
	 * Save macro.
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
		Language language;

		int length = definitions.length;
		for (int i=1; i<length; i++) {
			if (macros.containsKey(definitions[i])) {
				if (i+1 < length && this.isNumeric(definitions[i + 1])) {
					int nb = Integer.parseUnsignedInt(definitions[i + 1]);
					for (int cpt=0; cpt < nb; cpt++) {
						instrList.addAll(macros.get(definitions[i]));
					}
					i += 1;
				} else {
					instrList.addAll(macros.get(definitions[i]));
				}
			} else {
				if ((language = Language.languageMap.get(definitions[i])) != null) {
					instrList.add(language);
				} else {
					for (char inst: definitions[i].toCharArray()) {
						if ((language = Language.languageMap.get("" + inst)) == null) {
							throw  new SyntaxErrorException("Bad macro definition : " + definitions[i]);
						}
						instrList.add(language);
					}
				}
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
	private boolean writeMacro(String instruction) throws IOException, BracketsParseException {
		String[] definitions = instruction.split("\\s+");


		if (macros.containsKey(definitions[0])) {
			int length = definitions.length;
			int number = 1;


			if (2 <= length && this.isNumeric(definitions[1])) {
				number = Integer.parseUnsignedInt(definitions[1]);
			}
			for (int i=0; i < number; i++) {
				for (Language instr: macros.get(definitions[0]))
					write(instr);
			}
			for (int i=2; i<length; i++)
				write(definitions[i]);

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
	private void writeInstructionAndMacro(String instruction) throws IOException, BracketsParseException {
		Language currentInstruction = null;

		if ((currentInstruction = Language.languageMap.get(instruction)) != null) {
			write(currentInstruction);
		} else if (macros.containsKey(instruction)) {
			writeMacro(instruction);
		} else {
			if (!writeMacro(instruction))
				write(instruction);
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
	private void writeAll() throws IOException, BracketsParseException, java.io.IOException {
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

		while (!endofCompile && (instruction = reader.getNext()) != null) {
			if (Language.languageMap.get(instruction) == null &&
					instruction.charAt(0) == BfReader.PREPROCESSING) {
				this.saveMacro(instruction);
			} else {
				endofCompile = true;
				writeInstructionAndMacro(instruction);
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
	public Pair<Reader, JumpTable> compile(List<ContextExecuter> contextExecuters) throws IOException, SyntaxErrorException, BracketsParseException, java.io.IOException {
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
	private Pair<Reader, JumpTable> endCompile(List<ContextExecuter> contextExecuters) throws BracketsParseException, IOException {
		if (contextExecuters.contains(Context.CHECK.getContextExecuter())){
			System.out.println(contextExecuters);
			jumpTable.finish();
		}
		try {
			writer.close();
		} catch (java.io.IOException e1) {
			throw new IOException();
		}
		try {
			return new Pair<>(new BfReader(tmpFile.getName()), jumpTable);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	/**
	 * Write.
	 *
	 * @param currentInstruction
	 *            the current instruction
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void write(String currentInstruction) throws IOException {
		try {
			writer.write(currentInstruction);
		} catch (java.io.IOException e) {
			throw new IOException();
		}
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
		write(currentInstruction.getShortSyntax());
		jumpTable.addInstruction(currentInstruction, ++pos);
	}

}
