/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.brainfuck.common;

/**
 *
 * @author davidLANG
 */
public interface InterpretorInterface {
    public void execute(Memory machine);
}

// Concret class for indent
class IncremanteExecute implements InterpretorInterface { 
    @Override
    public void execute(Memory machine) {
        try {
            machine.incr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Concret class for right
class DecremanteExecute implements InterpretorInterface {
    @Override
    public void execute(Memory machine) {
        try {
            machine.decr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Concret class for right
class RightExecute implements InterpretorInterface { 
    @Override
    public void execute(Memory machine) {
        machine.right();
    }
}

// Concret class for right
class LeftExecute implements InterpretorInterface {
    @Override
    public void execute(Memory machine) {
        machine.left();
    }
}
