
package net.brainfuck.interpreter;

import java.util.HashMap;
import java.util.Map;

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

public class Interpreter {
    private Map<String, InterpreterInterface> intrepretorExecuter = new HashMap<>();
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

        // Initialisation du language
        IncremanteExecute incrExecute = new IncremanteExecute();
        DecremanteExecute decremanteExecute = new DecremanteExecute();
        RightExecute rightExecute = new RightExecute();
        LeftExecute leftExecute = new LeftExecute();

        this.intrepretorExecuter.put("INCR", incrExecute);
        this.intrepretorExecuter.put("RIGHT", rightExecute);
        this.intrepretorExecuter.put("LEFT", leftExecute);
        this.intrepretorExecuter.put("DECR", decremanteExecute);
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
            InterpreterInterface interpretor = this.intrepretorExecuter.get(instruction);
            if (interpretor == null) {
                throw new SyntaxErrorException(instruction);
            }
            interpretor.execute(memory);
        }
        reader.close();
    }
    
}

