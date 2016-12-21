package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.Exception;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.intoutinsruction.InInstruction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Hiltcher
 */
public class InInstructionTest {
	private Memory memory;
	private String filename;
	private ExecutionReader reader;
	private InInstruction instruction;

	/**
	 * Sets the up.
	 *
	 */
	@Before
	public void setUp() throws Exception {


		List<Language> langage = Arrays.asList(Language.RIGHT,Language.RIGHT);

		reader = new ExecutionReader(langage, null);
		memory = new Memory();
		InputStreamReader inStream = new InputStreamReader(new InputStream() {
			int i = 0;
			@Override
			public int read() throws IOException {
				if(++i < 100) return 5 + i;
				return '\0';
			}
		});
		instruction = new InInstruction(inStream);

	}

	/**
	 * In.
	 *
	 */
	@Test
	public void in() throws Exception {
		instruction.execute(memory);
		assertEquals(6, memory.get());
	}

	/**
	 * End of In.
	 *
	 */
	@Test(expected = FileNotFoundIn.class)
	public void endIn() throws Exception {
		InputStreamReader inStream = new InputStreamReader(new InputStream() {
			@Override
			public int read() throws IOException {
				return -1;
			}
		});
		instruction = new InInstruction(inStream);
		instruction.execute(memory);
	}

	/**
	 * Simule empty file.
	 * An FileNotFoundIn Exception may happens.
	 *
	 */
	@Ignore
	@Test(expected = net.brainfuck.exception.FileNotFoundIn.class)
	public void badIn() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		System.setIn(inputStream);
		instruction.execute(memory);
	}


// TODO : test ici
	/**
	 * Rewrite long.
	 *
	@Test
	public void rewriteLong() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		String data = "IN";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		reader = new BfReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new InInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals(",", outputStream.toString());
	}
	 */

	/**
	 * Rewrite col.
	 *
	@Test
	public void rewriteCol() throws Exception, FileNotFoundException {
		filename = "filename.bmp";
		String data = "ffff00";
		BfImageWriter writer = new BfImageWriter(new FileOutputStream(filename));
		writer.write(data);
		writer.close();
		reader = new BfImageReader(filename);
		memory = new Memory();
		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
		instruction = new InInstruction();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		instruction.rewrite();
		assertEquals(",", outputStream.toString());
	}
	 */

	/**
	 * Translate.
	 *
	 *
	 */
	@Test
	public void translate() throws Exception {
		InInstruction instruction = new InInstruction(null);
		assertEquals("ffff00", instruction.translate() );
	}


}