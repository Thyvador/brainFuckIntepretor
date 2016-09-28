
package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author davidLANG
 */

public class Interpretor {
    private Map<String, InterpretorInterface> intrepretorExecuter = new HashMap<>();
    private Memory memory;
    private Reader reader;
    
    
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
    
    
    public void interprate() throws IOException, java.io.IOException, MemoryOutOfBoundsException {
        while (reader.hasNext()) {
            String instruction = reader.getNext();
        
            InterpretorInterface interpretor = this.intrepretorExecuter.get(instruction);
            if (interpretor == null) {
                // TO DO
                // Le cas ou le token n'existe pas dans le language
            }
            interpretor.execute(memory);
        }
        reader.close();
    }
    
}

