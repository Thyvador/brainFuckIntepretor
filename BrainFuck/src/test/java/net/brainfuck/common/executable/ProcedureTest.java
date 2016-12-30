package net.brainfuck.common.executable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import net.brainfuck.common.Pair;
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
		Procedure procedure = new Procedure("test",  null, new ArrayList<String>());
		Pair pair = new Pair(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		procedure.addPair(pair);
		assertEquals("void test (int *ptr) {\n\n\n"
				+ "(*ptr)++;(*ptr)++;\n"
				+ "}\n\n", procedure.generate());
	}
	
	@Test
	public void testGenerateWithArgs() {
		Procedure procedure = new Procedure("test",  null, Arrays.asList(new String[] {"arg1", "arg2"}));
		Pair pair = new Pair(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		procedure.addPair(pair);
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
