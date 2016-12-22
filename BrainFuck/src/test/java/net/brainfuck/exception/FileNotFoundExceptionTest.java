package net.brainfuck.exception;

import org.junit.Test;

import net.brainfuck.io.BfReader;

/**
 * The Class FileNotFoundExceptionTest.
 *
 * @author Francois Melkonian
 * @date 19/11/2016
 */
public class FileNotFoundExceptionTest {
	
	/**
	 * No file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = net.brainfuck.exception.FileNotFoundException.class)
	public void noFile() throws java.lang.Exception{
		BfReader reader = new BfReader("notAFile.void");
	}

}