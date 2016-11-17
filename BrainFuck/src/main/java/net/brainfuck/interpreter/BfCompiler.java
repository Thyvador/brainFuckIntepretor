package net.brainfuck.interpreter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.brainfuck.common.BfReader;
import net.brainfuck.common.Pair;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;

public class BfCompiler {

	private Reader reader;
	private Writer writer;
	private File tmpFile;
	private JumpTable jumpTable;
	private int pos = 0;
	
	//private Map<String, String>

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

	public Pair<Reader, JumpTable> compile() throws IOException, SyntaxErrorException, BracketsParseException {
		String instruction;
		Language currentInstruction;
		
		Map<String, List<Language>> macros = new HashMap<>();

		while ((instruction = reader.getNext()) != null) {
			if ((currentInstruction = Language.languageMap.get(instruction)) == null) {
				if (instruction.charAt(0) == BfReader.PREPROCESSING) {
					String[] macro = instruction.split(""+(char)BfReader.PREPROCESSING);
					List<Language> instrList = new ArrayList<>();
					for (char instr: macro[2].toCharArray())
						instrList.add(Language.languageMap.get(""+instr));
					macros.put(macro[1], instrList);
				} else if (macros.containsKey(instruction)) {
					for (Language instr: macros.get(instruction))
						write(instr);
				} else {
					throw new SyntaxErrorException(instruction);
				}
			} else {
				write(currentInstruction);
			}
		}
		jumpTable.finish();
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
	
	private void write(Language currentInstruction) throws IOException, BracketsParseException {
		try {
			writer.write(currentInstruction.getShortSyntax());
			jumpTable.addInstruction(currentInstruction, ++pos);
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

}
