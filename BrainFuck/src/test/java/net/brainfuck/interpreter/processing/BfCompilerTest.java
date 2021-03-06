package net.brainfuck.interpreter.processing;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;

/**
 * The Class BfCompilerTest.
 */
public class BfCompilerTest {
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		String filename = "filename.bf";
		String data = "!test ++ ++\n"
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
	}


	/**
	 * Test compile.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws SyntaxErrorException
	 *             the syntax error exception
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 * @throws IncorrectArgumentException
	 *             the incorrect argument exception
	@Test
	public void testCompile() throws net.brainfuck.exception.IOException, FileNotFoundException, SyntaxErrorException, BracketsParseException, IOException, IncorrectArgumentException {
		Executer executer = new Executer(new ArgumentAnalyzer(new String[]{"-p","filename.bf"}));
		Reader r = new BfCompiler(new BfReader("filename.bf"),executer.getContextExecuters()).compile(executer.getContextExecuters()).getFirst();
		StringBuilder res = new StringBuilder();
		String instruction;
		while((instruction = r.getNext()) != null)
			res.append(instruction);
		assertEquals("+++++++++++++---", res.toString());
	}
	 */

	
	
}
