package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.Procedure;
import net.brainfuck.executer.Context;
import net.brainfuck.executer.Executer;
import org.junit.Test;

import java.util.Arrays;

import static net.brainfuck.interpreter.Language.*;

/**
 * Created by thyvador on 22/12/16.
 */
public class InterpreterTest {

    @Test
    public void test() throws Exception{
        Memory m = new Memory();
        Language.setInstructions(null,null);
        Executable executable = new Procedure("yoloproc", Arrays.asList(RIGHT, INCR, INCR, LEFT, RIGHT, RIGHT),new JumpTable(false),m);
        ArgumentAnalyzer arg = new ArgumentAnalyzer(new String[]{"-p","yolo"});
        Context.setExceuter(null);
        Executer e = new Executer(arg);
	    e.setArgumentExecuter(m,null);
	    System.out.println(e.getContextExecuters());
        Interpreter i = new Interpreter(e,executable);
        i.interprate();
	    System.out.println(m);
    }

}