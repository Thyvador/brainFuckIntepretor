package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.Exception;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.BackInstruction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The Class BackInstructionTest.
 *
 * @author Alexandre, Francois Melkonian
 */
public class BackInstructionTest {
    private Memory memory;
    private ExecutionReader reader;
    private BackInstruction instruction;

    /**
     * Create a.
     */
    @Before
    public void setUp() throws Exception {
        Language.setInstructions(null, null);
        List<AbstractInstruction> langage = Arrays.asList(Language.JUMP.getInterpreter(), Language.BACK.getInterpreter());
        JumpTable jumpTable = new JumpTable(false);
        jumpTable.addInstruction(langage.get(0), 1);
        jumpTable.addInstruction(langage.get(1), 0);
        reader = new ExecutionReader(langage, jumpTable);
        Language.setExecutable(reader);
        memory = new Memory();
        instruction = new BackInstruction(reader);
    }

    /**
     * Back.
     */
    //La case mémoire et à 0, ca passe.
    @Test
    public void back() throws Exception {
        memory.set(2);
        reader.getNext();
        AbstractInstruction instruction = reader.getNext();
        instruction.execute(memory);
        assertEquals(0, reader.getExecutionPointer());
    }

    /**
     * Do not back.
     */
    @Test
    public void doNotBack() throws Exception {
        memory.set(0);
        reader.getNext();
        AbstractInstruction instruction = reader.getNext();
        instruction.execute(memory);
        assertEquals(1, reader.getExecutionPointer());
    }


    /**
     * Parenthizing error.
     */
    @Test(expected = BracketsParseException.class)
    public void parenthizingError() throws Exception {
        Language.setInstructions(null, null);
        List<AbstractInstruction> langage = Arrays.asList(Language.IN.getInterpreter(), Language.BACK.getInterpreter());
        JumpTable jumpTable = new JumpTable(false);
        jumpTable.addInstruction(langage.get(1), 0);
        reader = new ExecutionReader(langage, jumpTable);
        Language.setExecutable(reader);
        memory = new Memory();
        instruction = new BackInstruction(reader);
        memory.set(2);
        reader.getNext();
        AbstractInstruction instruction = reader.getNext();
        instruction.execute(memory);
    }

    /**
     * Translate.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void translate() throws Exception {

        instruction = new BackInstruction(reader);
        assertEquals("ff0000", instruction.translate());
    }

    @Test
    public void testGenerate() throws Exception {
        assertEquals("}", instruction.generate());
    }

    @Test
    public void rewrite(){
        assertEquals("]", instruction.rewrite());
    }

}