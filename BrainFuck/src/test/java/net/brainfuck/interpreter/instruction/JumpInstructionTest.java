package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.*;
import net.brainfuck.common.Reader;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.BfCompiler;
import net.brainfuck.interpreter.JumpInstruction;
import net.brainfuck.interpreter.JumpTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

// TODO: Auto-generated Javadoc
/**
 * Created by Alexandre on 16/11/2016.
 */
public class JumpInstructionTest {
	private ArgumentInstruction argumentInstruction;
	private Memory memory;
	private Reader reader;
	private JumpInstruction instruction;
	private String filename;
	private String data;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		data = ">+[++]--[]";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new JumpInstruction();
	}

	/**
	 * Jump.
	 *
	 * @throws Exception
	 *             the exception
	 */
	//La case mémoire est à 0.
	@Test
	public void jump() throws Exception {
		reader.seek(3);
		instruction.execute(argumentInstruction);
		assertEquals(6, reader.getExecutionPointer());
	}

	/**
	 * Do not jump.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void doNotJump() throws Exception {
		memory.set(3);
		reader.seek(3);
		instruction.execute(argumentInstruction);
		assertEquals(3, reader.getExecutionPointer());
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		new File(filename).delete();

	}

	/**
	 * Rewrite long.
	 *
	 * @throws Exception
	 *             the exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void rewriteLong() throws Exception, IOException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "JUMP";
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
		instruction = new JumpInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("[", outputStream.toString());
	}

	/**
	 * Rewrite col.
	 *
	 * @throws Exception
	 *             the exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void rewriteCol() throws Exception, IOException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bmp";
		String data = "ff7f00";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		Executer executer = new Executer(new ArgumentAnalyzer(new String[]{"-p", "filename.bf"}));
		JumpTable jumpTable = new BfCompiler(reader,executer.getContextExecuters()).compile(executer.getContextExecuters()).getSecond();
		argumentInstruction = new ArgumentInstruction(memory, reader, jumpTable);
		instruction = new JumpInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals("[", outputStream.toString());
	}

	/**
	 * Translate.
	 *
	 * @throws Exception
	 *             the exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void translate() throws Exception, IOException {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "JUMP";
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
		instruction = new JumpInstruction();
		assertEquals("ff7f00",instruction.translate() );
	}
}