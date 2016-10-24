/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.brainfuck.interpreter;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 *
 * @author davidLANG
 */
interface InterpreterInterface {
    /**
     * Execute a method of Memory Class
     * @param memory Memory machine
     * @throws MemoryOutOfBoundsException throw by memory
     * @throws MemoryOverFlowException  throw by memory
     * @throws IOException throw by memory
     */
    void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn;

    /**
     * Print the short syntax of the command wich implement this interface
     */
    void rewrite();
}

