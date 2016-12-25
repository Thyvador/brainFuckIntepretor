package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.BackInstruction;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The Class BackInstructionTest.
 *
 * @author Alexandre, Francois Melkonian
 */
// TODO: La création des exécutables a cassé les test sur Back
public class BackInstructionTest {
    private Memory memory;
    private ExecutionReader reader;
    private BackInstruction instruction;

    /**
     * Create a.
     */
    @Before
    public void setUp() throws Exception {

        List<Language> langage = Arrays.asList(Language.RIGHT, Language.RIGHT);
        reader = new ExecutionReader(langage, null);
        memory = new Memory();
        instruction = new BackInstruction(reader);
    }

    /**
     * Back.
     */
    //La case mémoire et à 0, ca passe.
    @Test
    @Ignore
    public void back() throws Exception {
        reader.seek();
        instruction.execute(memory);
        assertEquals(6, reader.getExecutionPointer());
    }

    /**
     * Do not back.
     */
    @Test
    @Ignore
    public void doNotBack() throws Exception {
        memory.set(2);
        reader.seek();
        instruction.execute(memory);
        assertEquals(3, reader.getExecutionPointer());
    }


    /**
     * Parenthizing error.
     */
    @Ignore
    @Test(expected = BracketsParseException.class)
    public void parenthizingError() throws Exception {
//		Charset charset = Charset.forName("UTF-8");
//		filename = "filename.bf";
//		String data = "]";
//		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
//			writer.write(data, 0, data.length());
//		} catch (IOException x) {
//			System.err.format("IOException: %s%n", x);
//		}
//		reader = new BfReader(filename);
//		memory = new Memory();
//		argumentInstruction = new ArgumentInstruction(memory, reader, new JumpTable(reader));
//		instruction = new BackInstruction();
//		instruction.execute(argumentInstruction);
        // TODO: 21/12/16 A refaire
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

}