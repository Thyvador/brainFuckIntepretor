package net.brainfuck.common.executable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;

public class ProcedureTest {
	Procedure procedure;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGenerateWithoutArgs() {
		procedure = new Procedure("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), 
				null, null, new ArrayList<String>());
		assertEquals("void test (int *ptr) {\n\n\n"
				+ "(*ptr)++;(*ptr)++;\n"
				+ "}\n\n", procedure.generate());
	}
	
	@Test
	public void testGenerateWithArgs() {
		procedure = new Procedure("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), 
				null, null, Arrays.asList(new String[] {"arg1", "arg2"}));
		assertEquals("void test (int *ptr, int arg1, int arg2) {\n\n"
				+ "(*(ptr++)) = arg1;(*(ptr++)) = arg2;\n"
				+ "(*ptr)++;(*ptr)++;\n"
				+ "}\n\n", procedure.generate());
	}

	@Test
	public void testTrace() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseReader() {
		fail("Not yet implemented");
	}

}
