package net.brainfuck.common.executable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.brainfuck.common.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;

public class ProcedureFunctionExecuteTest {

	ProcedureFunctionExecute pfe;
	
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	@Test
	public void testRewrite() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testTranslate() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGenerateProcedureWithoutArgs() throws Exception {
		Procedure procedure = new Procedure("test",  null, new ArrayList<String>());
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		procedure.addPair(pair);
		pfe = new ProcedureFunctionExecute(new ArrayList<Integer>(), procedure);
		System.out.println(pfe.generate());
		assertEquals("ptr++;test(ptr);", pfe.generate());
	}

	@Test
	public void testGenerateProcedureWithArgs() throws Exception {
		Procedure procedure = new Procedure("test", null, Arrays.asList(new String[] {"arg1", "arg2"}));
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>( Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		procedure.addPair(pair);
		pfe = new ProcedureFunctionExecute(Arrays.asList(new Integer[]{1, 2}), procedure);
		assertEquals("ptr++;test(ptr, *(start_scope+1), *(start_scope+2));", pfe.generate());
	}
	
	@Test
	public void testGenerateFunctionWithoutArgs() throws Exception {
		Function function = new Function("test",  null, new ArrayList<String>());
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		function.addPair(pair);
		pfe = new ProcedureFunctionExecute(new ArrayList<Integer>(), function);
		System.out.println(pfe.generate());
		assertEquals("ptr++;(*ptr) = test(ptr);", pfe.generate());
	}

	@Test
	public void testGenerateFunctionWithArgs() throws Exception {
		Function function =  new Function("test",  null, Arrays.asList(new String[] {"arg1", "arg2"}));
		Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(Arrays.asList(new AbstractInstruction[] {new IncrementInstruction(), new IncrementInstruction()}), null);
		function.addPair(pair);
		pfe = new ProcedureFunctionExecute(Arrays.asList(new Integer[]{1, 2}), function);
		assertEquals("ptr++;(*ptr) = test(ptr, *(start_scope+1), *(start_scope+2));", pfe.generate());
	}

	@Ignore
	@Test
	public void testTrace() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

}
