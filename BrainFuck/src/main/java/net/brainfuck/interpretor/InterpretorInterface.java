/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 *
 * @author davidLANG
 */
public interface InterpretorInterface {
    public void execute(Memory machine) throws MemoryOutOfBoundsException;
}

