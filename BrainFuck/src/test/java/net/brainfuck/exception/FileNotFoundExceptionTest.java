package net.brainfuck.exception;

import net.brainfuck.common.BfReader;
import org.junit.Test;

import java.lang.*;

import static org.junit.Assert.*;

/**
 * @author Francois Melkonian
 * @date 19/11/2016
 */
public class FileNotFoundExceptionTest {
	@Test(expected = net.brainfuck.exception.FileNotFoundException.class)
	public void noFile() throws java.lang.Exception{
		BfReader reader = new BfReader("notAFile.void");
	}

}