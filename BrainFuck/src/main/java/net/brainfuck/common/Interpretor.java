/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.brainfuck.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author davidLANG
 */
public class Interpretor {
    private Map<String,InterpretorInterface> intrepretorExecuter = new HashMap<>();
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
    
    
    public void interprate() {
        while (reader.endOfFile() == false) {
            String instruction = reader.readOneInstruction();
        
            InterpretorInterface interpretor = this.intrepretorExecuter.get(instruction);
            interpretor.execute(memory);
        }
    }
    
}


