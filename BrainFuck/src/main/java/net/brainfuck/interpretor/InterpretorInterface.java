/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.brainfuck.interpretor;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 *
 * @author davidLANG
 */
interface InterpretorInterface {
    /**
     * Execute a method of Memory Class
     * @param machine Memory machine
     */
    void execute(Memory machine) throws MemoryOutOfBoundsException, MemoryOverFlowException;
}

