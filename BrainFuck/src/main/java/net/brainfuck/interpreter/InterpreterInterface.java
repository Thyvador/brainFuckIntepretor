/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 *
 * @author davidLANG
 */
public interface InterpreterInterface {
    /**
     * Execute a method of Memory Class
     * @param memory Memory machine
     * @throws MemoryOutOfBoundsException throw by memory
     * @throws MemoryOverFlowException  throw by memory
     * @throws IOException throw by memory
     * @throws BracketsParseException 
     */
    void execute(Memory memory, Reader reader) throws MemoryOutOfBoundsException,
            MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException;
}

