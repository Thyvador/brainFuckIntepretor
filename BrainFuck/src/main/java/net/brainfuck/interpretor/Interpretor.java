
package net.brainfuck.interpretor;

import net.brainfuck.common.LineReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.exception.SynthaxeErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author davidLANG
 *
 */

public class Interpretor {
    private Map<String, InterpretorInterface> intrepretorExecuter = new HashMap<>();
    private Memory memory;
    private LineReader reader;

    /**
     * Initiliaze the hashmap wich contains
     * class which implements InterpretorInterface associate with  syntaxe
     * for example RightExecute is associate with >
     *
     * @param memory Memory
     * @param reader LineReader
     */
    public Interpretor(Memory memory, LineReader reader) {
        this.reader = reader;
        this.memory = memory;

        // Initialisation du language
        IncremanteExecute incrExecute = new IncremanteExecute();
        DecremanteExecute decremanteExecute = new DecremanteExecute();
        RightExecute rightExecute = new RightExecute();
        LeftExecute leftExecute = new LeftExecute();

        this.intrepretorExecuter.put("INCR", incrExecute);
        this.intrepretorExecuter.put("+", incrExecute);
        this.intrepretorExecuter.put("RIGHT", rightExecute);
        this.intrepretorExecuter.put(">", rightExecute);
        this.intrepretorExecuter.put("<", leftExecute);
        this.intrepretorExecuter.put("LEFT", leftExecute);
        this.intrepretorExecuter.put("-", decremanteExecute);
        this.intrepretorExecuter.put("DECR", decremanteExecute);
    }

    /**
     * Interprate all characters wich can be read with the attribute reader
     *
     * @throws SynthaxeErrorException {@link SynthaxeErrorException}
     * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException}
     * @throws IOException {@link IOException}
     * @throws MemoryOverFlowException {@link MemoryOverFlowException}
     */
    public void interprate() throws IOException, SynthaxeErrorException, MemoryOutOfBoundsException, MemoryOverFlowException {
        while (reader.hasNext()) {
            String instruction = reader.getNext();
        
            InterpretorInterface interpretor = this.intrepretorExecuter.get(instruction);
            if (interpretor == null) {
                throw new SynthaxeErrorException(instruction);
            }
            interpretor.execute(memory);
        }
        reader.close();
    }
    
}

