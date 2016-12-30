package net.brainfuck.common.executable;

import static org.junit.Assert.*;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import org.junit.Before;
import org.junit.Ignore;
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


	@Ignore
	@Test
	public void testTrace() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecute() throws MemoryOutOfBoundsException, BracketsParseException, SegmentationFaultException, MemoryOverFlowException, FileNotFoundIn, IOException {
		Memory memory = new Memory();
		procedure= new Procedure("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(),
				new IncrementInstruction()}),null, memory, new ArrayList<String>());

		memory.lock();
		procedure.execute(memory);
		assertEquals(2, memory.get());
	}

	@Test(expected = BracketsParseException.class)
	public void testCloseReader() throws BracketsParseException, MemoryOutOfBoundsException {
		Memory memory = new Memory();
		procedure = new Procedure("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(),
				new IncrementInstruction()}),null, memory, new ArrayList<String>());
		procedure.closeReader();
	}

	@Test
	public void testCloseReader2() throws BracketsParseException, MemoryOutOfBoundsException {
		Memory memory = new Memory();
		procedure = new Procedure("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(),
				new IncrementInstruction()}),null, memory, new ArrayList<String>());
		memory.lock();
		procedure.closeReader();
		assertTrue(memory.isScopeEmpty());
	}


}
