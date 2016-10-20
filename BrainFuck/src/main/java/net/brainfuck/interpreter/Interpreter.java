
package net.brainfuck.interpreter;

import static net.brainfuck.interpreter.Language.DECR;
import static net.brainfuck.interpreter.Language.INCR;
import static net.brainfuck.interpreter.Language.LEFT;
import static net.brainfuck.interpreter.Language.RIGHT;

import java.util.HashMap;
import java.util.Map;

import net.brainfuck.common.ArgumentAnalyzer;
import net.brainfuck.common.ArgumentConstante;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SyntaxErrorException;

/**
 * @author davidLANG
 *
 */

public class  Interpreter {
    private Map<String, InterpreterInterface> interpretorExecuter = new HashMap<>();
    private Memory memory;
    private Reader reader;
    private boolean[] flags;

    /**
     * Constructor which initialize attribute.
     * @param memory Memory
     * @param reader Reader
     * @param arg ArgumentAnalyzer use to get arguments
     */
    public Interpreter(Memory memory, Reader reader, ArgumentAnalyzer arg) {
        this.reader = reader;
        this.memory = memory;
        this.flags = arg.getFlags();
        this.initLanguages();
    }



    /**
     * Interpret all characters which can be read with the attribute reader.
     *
     * @throws SyntaxErrorException {@link SyntaxErrorException} if an error of syntax is found.
     * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException} if memory throw an exception.
     * @throws IOException {@link IOException}  if reader throw an exception.
     * @throws MemoryOverFlowException throw by memory
     */
    public void interprate() throws IOException, SyntaxErrorException , MemoryOutOfBoundsException, MemoryOverFlowException {
        String instruction;
        InterpreterInterface interpretor;

        while ((instruction = reader.getNext()) != null) {
            if ((interpretor = this.interpretorExecuter.get(instruction)) == null) {
                throw new SyntaxErrorException(instruction);
            }

            if (flags[ArgumentConstante.REWRITE]) {
                interpretor.rewrite();
            }else if (flags[ArgumentConstante.CHECK]) {
                interpretor.execute(memory);
            }
        }
        reader.close();
    }

    /**
     * Initialize the Hashmap which contains
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

