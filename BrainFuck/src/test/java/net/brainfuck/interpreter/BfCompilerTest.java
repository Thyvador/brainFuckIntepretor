package net.brainfuck.interpreter;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.common.BfReader;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.SyntaxErrorException;

public class BfCompilerTest {

	BfCompiler compiler;
	private String filename;
	private String data;
	
	@Before
	public void setUp() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		data = "!test ++ ++\n"
				+ "!decr -\n"
				+ "+\n"
				+ "test\n"
				+ "test 2\n"
				+ "decr 3";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		compiler = new BfCompiler(new BfReader(filename));
	}

	@Test
	public void testCompile() throws net.brainfuck.exception.IOException, FileNotFoundException, SyntaxErrorException, BracketsParseException, IOException {
		Reader r = compiler.compile().getFirst();
		StringBuilder res = new StringBuilder();
		String instruction;
		while((instruction = r.getNext()) != null)
			res.append(instruction);
		assertEquals(res.toString(), "+++++++++++++---");
	}

	
	
}
