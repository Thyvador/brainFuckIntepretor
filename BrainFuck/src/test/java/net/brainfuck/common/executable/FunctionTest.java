package net.brainfuck.common.executable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Pair;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SegmentationFaultException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;

public class FunctionTest {
	
	Function function;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGenerateWithoutArgs() {
		Function function = new Function("test",  null, new ArrayList<String>());
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		function.addPair(pair);
		assertEquals("int test (int *ptr) {\n"
				+ "const int *start_scope = ptr;\n\n"
				+ "(*ptr)++;(*ptr)++;\n"
				+ "return *ptr;\n"
				+ "}\n\n", function.generate());
	}
	
	@Test
	public void testGenerateWithArgs() {
		Function function = new Function("test",  null, Arrays.asList(new String[] {"arg1", "arg2"}));
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		function.addPair(pair);
		assertEquals("int test (int *ptr, int arg1, int arg2) {\n"
				+ "const int *start_scope = ptr;\n"
				+ "(*(ptr++)) = arg1;(*(ptr++)) = arg2;\n"
				+ "(*ptr)++;(*ptr)++;\n"
				+ "return *ptr;\n"
				+ "}\n\n", function.generate());
	}

	@Ignore
	@Test
	public void testTrace() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecute() throws MemoryOutOfBoundsException, BracketsParseException, SegmentationFaultException, MemoryOverFlowException, FileNotFoundIn, IOException {
		Memory memory = new Memory();
		function = new Function("test", memory, new ArrayList<String>());
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(),
				new IncrementInstruction()}),null);
		function.addPair(pair);
		memory.lock();
		function.execute(memory);
		assertEquals(2, memory.get());
		}

	@Test(expected = BracketsParseException.class)
	public void testCloseReader() throws BracketsParseException, MemoryOutOfBoundsException {
		Memory memory = new Memory();
		function = new Function("test", memory, new ArrayList<String>());
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(),
				new IncrementInstruction()}),null);
		function.addPair(pair);
		function.closeReader();
	}

	@Test
	public void testCloseReader2() throws BracketsParseException, MemoryOutOfBoundsException {
		Memory memory = new Memory();
		function = new Function("test", memory, new ArrayList<String>());
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(),
				new IncrementInstruction()}),null);
		function.addPair(pair);
		memory.lock();
		function.closeReader();
		assertTrue(memory.isScopeEmpty());
	}


}
