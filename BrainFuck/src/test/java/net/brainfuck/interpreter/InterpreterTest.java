package net.brainfuck.interpreter;

import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.common.executables.Procedure;
import org.junit.Test;

import java.util.Arrays;

import static net.brainfuck.interpreter.Language.*;

/**
 * Created by thyvador on 22/12/16.
 */
public class InterpreterTest {

    @Test
    public void test(){
        ExecutionReader executionReader = new ExecutionReader(
                Arrays.asList(RIGHT, INCR, INCR, LEFT, RIGHT, RIGHT),null);
    }

}