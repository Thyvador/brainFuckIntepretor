
package net.brainfuck.common;

import net.brainfuck.common.exception.IOException;

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
                
        // Initialisation du language
        IncremanteExecute incrExecute = new IncremanteExecute();
        RightExecute rightExecute = new RightExecute();
        
        this.intrepretorExecuter.put("INCR", incrExecute);
        this.intrepretorExecuter.put("+", incrExecute);
        this.intrepretorExecuter.put("RIGHT", rightExecute);
        this.intrepretorExecuter.put(">", rightExecute);
    }
    
    
    public void interprate() throws IOException, java.io.IOException {
        while (reader.hasNext()) {
            String instruction = reader.getNext();
        
            InterpretorInterface interpretor = this.intrepretorExecuter.get(instruction);
            interpretor.execute(memory);
        }
    }
    
}

