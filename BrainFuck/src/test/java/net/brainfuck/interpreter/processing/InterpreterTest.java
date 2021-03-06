package net.brainfuck.interpreter.processing;

import static net.brainfuck.interpreter.Language.INCR;
import static net.brainfuck.interpreter.Language.LEFT;
import static net.brainfuck.interpreter.Language.RIGHT;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Pair;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.Procedure;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.Interpreter;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Created by thyvador on 22/12/16.
 */
public class InterpreterTest {

    @Test
    public void test() throws Exception{
        Memory m = new Memory();
        Language.setInstructions(null,null);
        Executable executable = new Procedure(
                "yoloproc",
                m,
                null
        );
        Pair<List<AbstractInstruction>, JumpTable> pair =  new Pair<>(
                Arrays.asList(RIGHT.getInterpreter(),
                        INCR.getInterpreter(),
                        INCR.getInterpreter(),
                        LEFT.getInterpreter(),
                        RIGHT.getInterpreter(),
                        RIGHT.getInterpreter()),
                new JumpTable(false)
        );
        executable.addPair(pair);
        m.lock();
        ArgumentAnalyzer arg = new ArgumentAnalyzer(new String[]{"-p","yolo"});
        Context.setExecuter(null, null);
        Executer e = new Executer(arg);
	    e.setArgumentExecuter(m,null);
        Interpreter i = new Interpreter(e,executable);
        i.interprate();
        m.right();
        assertEquals(2, m.get());
    }


}