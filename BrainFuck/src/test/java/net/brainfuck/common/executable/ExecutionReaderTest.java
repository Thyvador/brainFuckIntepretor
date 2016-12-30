package net.brainfuck.common.executable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;

public class ExecutionReaderTest {

	ExecutionReader main;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGenerate() {
		main = new ExecutionReader(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		assertEquals("int main () {\n"
				+ "int *ptr = memory;\n\n"
				+ "(*ptr)++;(*ptr)++;\n"
				+ "return 0;\n"
				+ "}\n\n", main.generate());
	}

}
