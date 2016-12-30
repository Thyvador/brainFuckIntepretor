package net.brainfuck.common.executable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;

public class ProcedureFunctionExecuteTest {

	ProcedureFunctionExecute pfe;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRewrite() {
		fail("Not yet implemented");
	}

	@Test
	public void testTranslate() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGenerateProcedureWithoutArgs() throws Exception {
		pfe = new ProcedureFunctionExecute(new ArrayList<Integer>(), new Procedure("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), 
				null, null, new ArrayList<String>()));
		System.out.println(pfe.generate());
		assertEquals("ptr++;test(ptr);", pfe.generate());
	}

	@Test
	public void testGenerateProcedureWithArgs() throws Exception {
		pfe = new ProcedureFunctionExecute(Arrays.asList(new Integer[]{1, 2}), new Procedure("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), 
				null, null, Arrays.asList(new String[] {"arg1", "arg2"})));
		System.out.println(pfe.generate());
		assertEquals("ptr++;test(ptr, memory[1], memory[2]);", pfe.generate());
	}
	
	@Test
	public void testGenerateFunctionWithoutArgs() throws Exception {
		pfe = new ProcedureFunctionExecute(new ArrayList<Integer>(), new Function("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), 
				null, null, new ArrayList<String>()));
		System.out.println(pfe.generate());
		assertEquals("ptr++;(*ptr) = test(ptr);", pfe.generate());
	}

	@Test
	public void testGenerateFunctionWithArgs() throws Exception {
		pfe = new ProcedureFunctionExecute(Arrays.asList(new Integer[]{1, 2}), new Function("test", Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), 
				null, null, Arrays.asList(new String[] {"arg1", "arg2"})));
		System.out.println(pfe.generate());
		assertEquals("ptr++;(*ptr) = test(ptr, memory[1], memory[2]);", pfe.generate());
	}

	@Test
	public void testTrace() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

}
