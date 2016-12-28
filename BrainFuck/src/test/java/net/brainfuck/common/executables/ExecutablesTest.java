package net.brainfuck.common.executables;

import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static net.brainfuck.interpreter.Language.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by thyvador on 21/12/16.
 */
public class ExecutablesTest {
    private Executable executable;
    private List<Language> list;
    private JumpTable jumpTable;

    @Before
    public void setUp() throws Exception {
        list = Arrays.asList(RIGHT, LEFT, INCR, DECR, JUMP, BACK);
        jumpTable = new JumpTable(true);
        jumpTable.addInstruction(JUMP,4);
        jumpTable.addInstruction(BACK, 5);
        executable = new Executable(null, list, jumpTable) {
            @Override
            public Language getNext() {
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
        };

    }

    @Test
    public void getNext() throws Exception {
        assertEquals(RIGHT, executable.getNext());
        assertEquals(LEFT, executable.getNext());
        assertEquals(INCR, executable.getNext());
        assertEquals(DECR, executable.getNext());
        assertEquals(JUMP, executable.getNext());
        assertEquals(BACK, executable.getNext());
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
        for (int i = 0; i <list.size(); i++){
            executable.mark();
            assertEquals(i, (int) executable.getMarks().peek());
            executable.getNext();
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

    }


}