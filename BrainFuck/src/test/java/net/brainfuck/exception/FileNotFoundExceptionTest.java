package net.brainfuck.exception;

import net.brainfuck.common.BfReader;
import org.junit.Test;

import java.lang.*;

import static org.junit.Assert.*;

// TODO: Auto-generated Javadoc
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