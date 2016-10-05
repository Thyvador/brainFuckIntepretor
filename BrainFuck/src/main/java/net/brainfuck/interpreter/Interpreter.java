
package net.brainfuck.interpreter;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SyntaxErrorException;

import static net.brainfuck.interpreter.Language.*;

/**
 * @author davidLANG
 *
 */

public class Interpreter {
    private Map<String, InterpreterInterface> interpretorExecuter = new HashMap<>();
    private Memory memory;
    private Reader reader;

    /**
     * Initiliaze the hashmap wich contains
     * class which implements InterpreterInterface associate with  syntaxe
     * for example RightExecute is associate with >
     *
     * @param memory Memory
     * @param reader Reader
     */
    public Interpreter(Memory memory, Reader reader) {
        this.reader = reader;
        this.memory = memory;

        Language[] languages = new Language[]{INCR, DECR, RIGHT, LEFT};
        // Initialisation du language
        for (int i=0; i < languages.length; i++) {
            InterpreterInterface interpreter = languages[i].getInterpreter();
            String[] aliases = languages[i].getAliases();

            for (int cpt=0; cpt < aliases.length; i++) {
                this.interpretorExecuter.put(aliases[i], interpreter);
            }
        }
    }

    /**
     * Interprate all characters wich can be read with the attribute reader
     *
     * @throws SyntaxErrorException {@link SyntaxErrorException} if an error of syntax is found
     * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException} if memory throw an exception
     * @throws IOException {@link IOException}  if reader throw an exception
     */
    public void interprate() throws IOException, SyntaxErrorException , MemoryOutOfBoundsException, MemoryOverFlowException {
        while (reader.hasNext()) {
            String instruction = reader.getNext();
            InterpreterInterface interpretor = this.interpretorExecuter.get(instruction);
            if (interpretor == null) {
                throw new SyntaxErrorException(instruction);
            }
            interpretor.execute(memory);
        }
        reader.close();
    }
    
}

