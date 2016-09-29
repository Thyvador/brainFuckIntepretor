
package net.brainfuck.interpretor;

import java.util.HashMap;
import java.util.Map;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.SynthaxeErrorException;

/**
 * @author davidLANG
 *
 */

public class Interpretor {
    private Map<String, InterpretorInterface> intrepretorExecuter = new HashMap<>();
    private Memory memory;
    private Reader reader;

    /**
     * Initiliaze the hashmap wich contains
     * class which implements InterpretorInterface associate with  syntaxe
     * for example RightExecute is associate with >
     *
     * @param memory Memory
     * @param reader Reader
     */
    public Interpretor(Memory memory, Reader reader) {
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
     * @throws java.io.IOException {@link IOException}
     */
    public void interprate() throws IOException, java.io.IOException, SynthaxeErrorException, MemoryOutOfBoundsException {
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

