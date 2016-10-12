
package net.brainfuck.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.brainfuck.ArgumentAnalyzer;
import net.brainfuck.ArgumentConstante;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SyntaxErrorException;

import static net.brainfuck.interpreter.Language.*;

/**
 * @author davidLANG
 */

public class Interpreter {
    private Map<String, InterpreterInterface> interpretorExecuter = new HashMap<>();
    private Memory memory;
    private Reader reader;
    private boolean[] flags;

    /**
     * Constructor wich initialize atribut
     *
     * @param memory Memory
     * @param reader Reader
     */
    public Interpreter(Memory memory, Reader reader, ArgumentAnalyzer a) {
        this.reader = reader;
        this.memory = memory;
        this.flags = a.getFlags();
        this.initLanguages();
    }

    /**
     * Interprate all characters wich can be read with the attribute reader
     *
     * @throws SyntaxErrorException       {@link SyntaxErrorException} if an error of syntax is found
     * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException} if memory throw an exception
     * @throws IOException                {@link IOException}  if reader throw an exception
     */
    public void interprate() throws IOException, SyntaxErrorException, MemoryOutOfBoundsException, MemoryOverFlowException {
        while (reader.hasNext()) {
            String instruction = reader.getNext();
            InterpreterInterface interpretor = this.interpretorExecuter.get(instruction);
            if (interpretor == null) {
                throw new SyntaxErrorException(instruction);
            }
            if (!flags[ArgumentConstante.CHECK]) {
                interpretor.execute(memory);
            }
            if (flags[ArgumentConstante.REWRITE]) {
                interpretor.rewrite();
            }
        }
        reader.close();
    }

    /**
     * Initiliaze the hashmap wich contains
     * class which implements InterpreterInterface associate with  syntaxe
     * for example RightExecute is associate with >
     */
    private void initLanguages() {
        Language[] languages = new Language[]{INCR, DECR, RIGHT, LEFT};
        for (Language language : languages) {
            InterpreterInterface interpreter = language.getInterpreter();
            String[] aliases = language.getAliases();
            for (String aliase : aliases) {
                this.interpretorExecuter.put(aliase, interpreter);
            }
        }
    }

}

