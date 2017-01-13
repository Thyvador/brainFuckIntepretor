package net.brainfuck.interpreter.processing;

import static net.brainfuck.interpreter.Language.INCR;
import static net.brainfuck.interpreter.Language.RIGHT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Pair;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.Function;
import net.brainfuck.common.executable.Procedure;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IncorrectArgumentException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.ContextExecuter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.io.BfReader;
import net.brainfuck.io.Reader;


/**
 * @author davidLANG
 */
public class BfPrecompilerTest {
    private Map<String, AbstractInstruction> instructionMap;
    private Executable function;
    private Executable procedure;
    private BfPrecompiler bfPrecompiler;
    private Memory memory;

    @Before
    public void init() throws net.brainfuck.exception.IOException, IncorrectArgumentException, SyntaxErrorException, BracketsParseException, IOException, MemoryOutOfBoundsException {
        String name = "test";
        memory = new Memory();
        List<String> argument = Arrays.asList("arg1", "arg2", "arg3");
        List<AbstractInstruction> instructions = Arrays.asList(
                INCR.getInterpreter(),
                INCR.getInterpreter(),
                RIGHT.getInterpreter(),
                INCR.getInterpreter(),
                INCR.getInterpreter()
        );
        JumpTable jumpTable = new JumpTable(false);
        Pair<List<AbstractInstruction>, JumpTable> pair = new Pair<>(instructions, jumpTable);

        this.function = new Function(name, memory, argument);
        this.procedure = new Procedure(name, memory, argument);
        this.function.addPair(pair);
        this.procedure.addPair(pair);

        Language.setInstructionMap(new HashMap<>());
        Language.setInstructions(null, null);
        instructionMap = new HashMap<>(Language.getInstructionMap());
    }

    @Test
    public void analyzeFunction() throws net.brainfuck.exception.IOException, SyntaxErrorException, BracketsParseException, IOException, MemoryOutOfBoundsException {
        Reader reader = new BfReader("BrainFuck/src/test/resources/assets/brainfucktest/common/precompilerTest/test1.bf");
        List<ContextExecuter> contextExecuters = new ArrayList<>();
        contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        this.bfPrecompiler = new BfPrecompiler(reader);

        Assert.assertEquals(Language.getInstructionMap(), instructionMap);
        bfPrecompiler.analyzeProcedure(contextExecuters, new HashMap<>(), memory);
        Assert.assertNotEquals(Language.getInstructionMap(), instructionMap);
        instructionMap.put("test", function);
        Assert.assertEquals(Language.getInstructionMap(), instructionMap);
    }

    @Test
    public void analyzeProcedure() throws net.brainfuck.exception.IOException, SyntaxErrorException, BracketsParseException, IOException, MemoryOutOfBoundsException {
        Reader reader = new BfReader("BrainFuck/src/test/resources/assets/brainfucktest/common/precompilerTest/test2.bf");
        List<ContextExecuter> contextExecuters = new ArrayList<>();
        contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        this.bfPrecompiler = new BfPrecompiler(reader);


        Assert.assertEquals(Language.getInstructionMap(), instructionMap);
        bfPrecompiler.analyzeProcedure(contextExecuters, new HashMap<>(), memory);
        Assert.assertNotEquals(Language.getInstructionMap(), instructionMap);
        instructionMap.put("test", procedure);
        Assert.assertEquals(Language.getInstructionMap(), instructionMap);
    }

    @Test
    public void analyzeProcedureFunction() throws SyntaxErrorException, BracketsParseException, IOException, MemoryOutOfBoundsException, net.brainfuck.exception.IOException {
        Reader reader = new BfReader("BrainFuck/src/test/resources/assets/brainfucktest/common/precompilerTest/test3.bf");
        List<ContextExecuter> contextExecuters = new ArrayList<>();
        contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        this.bfPrecompiler = new BfPrecompiler(reader);


        Assert.assertEquals(Language.getInstructionMap(), instructionMap);
        bfPrecompiler.analyzeProcedure(contextExecuters, new HashMap<>(), memory);
        Assert.assertNotEquals(Language.getInstructionMap(), instructionMap);
        instructionMap.put("test", procedure);
        instructionMap.put("test2", function);
        Assert.assertEquals(Language.getInstructionMap(), instructionMap);
    }

    @Test
    public void analyzeEmptyFile() throws SyntaxErrorException, BracketsParseException, IOException, MemoryOutOfBoundsException, net.brainfuck.exception.IOException {
        Reader reader = new BfReader("BrainFuck/src/test/resources/assets/brainfucktest/common/precompilerTest/test0.bf");
        List<ContextExecuter> contextExecuters = new ArrayList<>();
        contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        this.bfPrecompiler = new BfPrecompiler(reader);


        Assert.assertEquals(Language.getInstructionMap(), instructionMap);
        bfPrecompiler.analyzeProcedure(contextExecuters, new HashMap<>(), memory);
        Assert.assertEquals(Language.getInstructionMap(), instructionMap);

    }

}
