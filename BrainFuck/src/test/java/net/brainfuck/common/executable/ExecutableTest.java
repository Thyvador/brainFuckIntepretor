package net.brainfuck.common.executable;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static net.brainfuck.interpreter.Language.*;
import static net.brainfuck.interpreter.Language.BACK;
import static net.brainfuck.interpreter.Language.JUMP;
import static org.junit.Assert.*;

/**
 * Created by alexh on 29/12/2016.
 */
public class ExecutableTest {
    private Executable executable;
    private List<AbstractInstruction> list;
    private JumpTable jumpTable;

    @Before
    public void setUp() throws Exception {
        Language.setInstructions(null, null);
        list = Arrays.asList(RIGHT.getInterpreter(), LEFT.getInterpreter(), INCR.getInterpreter(), DECR.getInterpreter(),
                JUMP.getInterpreter(), RIGHT.getInterpreter(), BACK.getInterpreter());
        jumpTable = new JumpTable(true);
        jumpTable.addInstruction(JUMP.getInterpreter(), 6);
        jumpTable.addInstruction(BACK.getInterpreter(), 4);
        executable = new Executable(null, list, jumpTable, null) {
            @Override
            public AbstractInstruction getNext() {
                return super.getNext();
            }

            @Override
            public void mark() {
                super.mark();
            }

            @Override
            public void reset() throws IOException, BracketsParseException {
                super.reset();
            }

            @Override
            public void unmark() throws BracketsParseException {
                super.unmark();
            }

            @Override
            public void seek() {
                super.seek();
            }

			@Override
			public String generate() {
				return null;
			}
        };
        Language.setExecutable(executable);

    }

    @Test
    public void getNext() throws Exception {
        assertEquals(RIGHT.getInterpreter(), executable.getNext());
        assertEquals(LEFT.getInterpreter(), executable.getNext());
        assertEquals(INCR.getInterpreter(), executable.getNext());
        assertEquals(DECR.getInterpreter(), executable.getNext());
        assertEquals(JUMP.getInterpreter(), executable.getNext());
        assertEquals(RIGHT.getInterpreter(), executable.getNext());
        assertEquals(BACK.getInterpreter(), executable.getNext());
        assertEquals(null, executable.getNext());
    }

    @Test(expected = BracketsParseException.class)
    public void errorCloseReader() throws Exception {
        executable.mark();
        executable.closeReader();
    }

    @Test
    public void closeReader() throws Exception {
        executable.closeReader();
    }

    @Test
    public void mark() throws Exception {
        for (int i = 0; i < list.size(); i++) {
            executable.getNext();
            executable.mark();
            assertEquals(i, (int) executable.getMarks().peek());
        }
    }

    @Test
    //Should work fine
    public void reset() throws Exception {
        executable.mark();
        executable.reset();
    }

    @Test(expected = BracketsParseException.class)
    public void errorReset() throws Exception {
        executable.reset();
    }

    @Test
    public void unmark() throws Exception {
        executable.mark();
        executable.unmark();
    }


    @Test(expected = BracketsParseException.class)
    public void errorUnmark() throws Exception {
        executable.unmark();
    }

    @Test
    public void seek() throws Exception {
        for (int i = 0; i < 5; i++) executable.getNext();
        executable.seek();
        assertEquals(null, executable.getNext());
    }

    @Test
    public void seek2() throws Exception {
        for (int i = 0; i < 7; i++) executable.getNext();
        executable.seek();
        assertEquals(RIGHT.getInterpreter(), executable.getNext());
    }


    @Test
    public void execute() throws Exception {
        Memory memory = new Memory();
        executable.execute(memory);
        for (int i = 0; i < 29999; i++) {
            assertEquals(0, memory.get());
            memory.right();
        }
    }

    @Test
    public void rewrite() throws Exception {
        assertEquals("><+-[>]", executable.rewrite());
    }

    @Test
    public void translate() throws Exception {
        assertEquals("0000ff9400d3ffffff4b0082ff7f000000ffff0000", executable.translate());
    }


}