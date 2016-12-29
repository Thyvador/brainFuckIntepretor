package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.Procedure;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import org.junit.Test;

import java.util.Arrays;

import static net.brainfuck.interpreter.Language.*;
import static org.junit.Assert.assertEquals;

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
                Arrays.asList(RIGHT.getInterpreter(),
                        INCR.getInterpreter(),
                        INCR.getInterpreter(),
                        LEFT.getInterpreter(),
                        RIGHT.getInterpreter(),
                        RIGHT.getInterpreter()),
                new JumpTable(false),
                m,
                null
        );
        m.lock();
        ArgumentAnalyzer arg = new ArgumentAnalyzer(new String[]{"-p","yolo"});
        Context.setExecuter(null, null);
        Executer e = new Executer(arg);
	    e.setArgumentExecuter(m,null);
        Interpreter i = new Interpreter(e,executable);
        i.interprate();
        assertEquals(2, m.get());
    }

}