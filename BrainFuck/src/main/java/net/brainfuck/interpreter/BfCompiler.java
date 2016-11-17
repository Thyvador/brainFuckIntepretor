package net.brainfuck.interpreter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

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

		int pos = 0;
		
		while ((instruction = reader.getNext()) != null) {
			if ((currentInstruction = Language.languageMap.get(instruction)) == null) {
				throw new SyntaxErrorException(instruction);
			}
			try {
				writer.write(currentInstruction.getShortSyntax());
				jumpTable.addInstruction(currentInstruction, ++pos);
			} catch (java.io.IOException e) {
				throw new IOException();
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

}
