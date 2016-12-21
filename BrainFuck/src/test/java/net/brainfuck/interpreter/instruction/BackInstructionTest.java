package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.BackInstruction;

import java.io.IOException;

/**
 * The Class BackInstructionTest.
 *
 * @author Alexandre,Francois Melkonian
 */
public class BackInstructionTest {
	private Memory memory;
	private ExecutionReader reader;
	private BackInstruction instruction;
	private String filename;

// TODO : ici
	/**
	 * Create a.
	 *
	@Before
	public void setUp() throws Exception {


		List<Language> langage = Arrays.asList(Language.RIGHT,Language.RIGHT);

		reader = new ExecutionReader(langage);
		memory = new Memory();
		instruction = new BackInstruction();
	}

	/**
	 * Back.
	 *
	//La case mémoire et à 0, ca passe.
	@Test
	public void back() throws Exception {
		reader.seek(6);
		instruction.execute(memory,reader);
		assertEquals(6, reader.getExecutionPointer());
	}
	 */

	/**
	 * Do not back.
	 *
	@Test
	public void doNotBack() throws Exception {
		memory.set(2);
		reader.seek(6);
		instruction.execute(memory,reader);

		assertEquals(3, reader.getExecutionPointer());
	}
	 */


	/**
	 * Parenthizing error.
	 *
	@Test (expected = BracketsParseException.class)
	public void parenthizingError() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "]";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new BackInstruction();
		instruction.execute(argumentInstruction);

	}	 */

//TODO : ici
	/**
	 * Rewrite long.
	 *
	@Test
	public void rewriteLong() throws Exception, IOException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "JUMP\nBACK";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		Executer executer = new Executer(new ArgumentAnalyzer(new String[]{"-p", "filename.bf"}));
		JumpTable jumpTable = new BfCompiler(reader,executer.getContextExecuters()).compile(executer.getContextExecuters()).getSecond();
		argumentInstruction = new ArgumentInstruction(memory, reader, jumpTable);
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		reader.getNext();
		instruction.rewrite();
		assertEquals("]", outputStream.toString());
	}
	 */

	/**
	 * Write "back" in a bmp file and read its
	 *
	@Test
	public void rewriteCol() throws Exception, IOException {
		filename = "filename.bmp";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write("ff7f00");
		writer.write("4b0082");
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		Executer executer = new Executer(new ArgumentAnalyzer(new String[]{"-p", "filename.bmp"}));
		JumpTable jumpTable = new BfCompiler(reader,executer.getContextExecuters()).compile(executer.getContextExecuters()).getSecond();
		argumentInstruction = new ArgumentInstruction(memory, reader, jumpTable);
		instruction = new BackInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		reader.getNext();
		instruction.rewrite();
		assertEquals("]", outputStream.toString());
	}
	 */


	/**
	 * Translate.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	@Test
	public void translate() throws Exception, IOException {

		instruction = new BackInstruction();
		assertEquals("ff0000", instruction.translate());
	}
	 */

	/**
	 * Delete test file
	 *
	@After
	public void tearDown() throws Exception {
		new File(filename).delete();

	}
	 */
}