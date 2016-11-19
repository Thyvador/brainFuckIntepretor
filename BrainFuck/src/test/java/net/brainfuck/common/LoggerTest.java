package net.brainfuck.common;

import org.junit.*;
import org.junit.experimental.categories.Categories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Alexandre on 16/11/2016.
 * @author Alexandre,Francois Melkonian
 */
public class LoggerTest {

	private Logger logger;
	private static String filename;
	private String data;
	private static String filenameLOG;

	@Before
	public void setUp() {
		logger = Logger.getInstance();
		filename ="Brainfuck/src/test/resources/assets/brainfucktest/common/yapi.bf";
		Charset charset = Charset.forName("UTF-8");
		filename = "filename.bf";
		filenameLOG = "filename.log";
		data = "++++++++++[>+>+++>+++++++>++++++++++<<<<-]>>>++.>+.+++++++..+++.<<++.>+++++++++++++++.>.+++.------.--------.<<+.<.";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
			writer.write(data, 0, data.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	/**
	 * Check if the resume contains all desired metrics if the program is empty.
	 * Another test check the EXEC_TIME, a test on it here will depend of the context.
	 * @throws Exception
	 */
	//@Ignore ("Probleme")
	@Test
	public void showResume() throws Exception {
		logger.reset();
		String resLogger = logger.showResume(new Memory());
		assertTrue(resLogger.contains("PROG_SIZE : 0\n") );
		assertTrue(resLogger.contains("EXEC_TIME : ") );
		assertTrue(resLogger.contains("EXEC_MOVE : 0\n") );
		assertTrue(resLogger.contains("DATA_MOVE : 0\n") );
		assertTrue(resLogger.contains("DATA_WRITE : 0\n") );
		assertTrue(resLogger.contains("DATA_READ : 0\n") );
	}

	@Test
	public void incrInstruction() throws Exception {
		logger.incrInstruction();
		assertEquals(1, logger.getNumInstructions());
	}

	/**
	 * Wait a random time ( between 0 and 500ms ) to simulate a program,
	 * then check if the logger return the real execution time.
	 * @throws Exception
	 */
	@Test
	public void startExecTime() throws Exception {
		long temps = System.currentTimeMillis();
		logger.startExecTime();
		int randomTimeExec = new Random().nextInt(500);
		while (System.currentTimeMillis() - temps<randomTimeExec){
		}

		String str = logger.showResume(new Memory());
		assertTrue(str.contains("EXEC_TIME : "+(System.currentTimeMillis()-temps)+" ms"));
	}

	@Test
	public void countMemoryWrite() throws Exception {
		logger.countMemoryWrite();
		assertEquals(1, logger.getNumMemoryWrite());
	}

	@Test
	public void countMemoryRead() throws Exception {
		logger.reset();
		logger.countMemoryRead();
		assertEquals(1, logger.getNumMemoryRead());
	}

	@Test
	public void countMemoryMove() throws Exception {
		logger.reset();
		logger.countMemoryMove();
		assertEquals(1, logger.getNumMemoryMove());
	}

	@Test
	public void countMove() throws Exception {
		logger.reset();
		logger.countMove();
		assertEquals(1, logger.getNumExecMove());
	}

	@Test
	public void incrStep() throws Exception {
		int i = logger.getStep();
		logger.reset();
		logger.incrStep();
		assertEquals(i + 1, logger.getStep());
	}


	@Test
	public void setWriter() throws Exception {
		logger.setWriter(filename);
		assertNotNull(logger.getWriter());
	}

	@Test
	public void write() throws Exception {
		logger.setWriter(filename);
		logger.write("message");
		List<String> str = Arrays.asList("============", "Error: ", "message");
		assertEquals(str, Files.readAllLines(Paths.get(filenameLOG)));
	}

	@Test
	public void write1() throws Exception {
		logger.setWriter(filename);
		logger.write(0, new Memory());
		List<String> str = Arrays.asList(
				"============",
				"Execution Step 1 : ",
				"Execution pointer : 0.",
				"Data pointer  : C0.",
				"Memory : ",
				"");
		assertEquals(str, Files.readAllLines(Paths.get(filenameLOG)));
	}

	@Test
	public void isWriterOpen() throws Exception {
		logger.setWriter(filename);
		assertTrue(logger.isWriterOpen());
	}
	@Test
	public void checkTimeExecution() throws Exception {
		assertTrue(logger.isWriterOpen());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void closeWriter() throws Exception {
		logger.setWriter(filename);
		logger.closeWriter();
		logger.write("message");
	}



	@AfterClass
	public static void cleanUp() throws IOException {
		new File(filenameLOG).delete();
		new File(filename).delete();
	}
}