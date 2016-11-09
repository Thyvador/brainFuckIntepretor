/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
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
public interface InstructionInterface {
    /**
     * Execute a method of Memory Class
     * @throws MemoryOutOfBoundsException throw by memory
     * @throws MemoryOverFlowException  throw by memory
     * @throws IOException throw by memory
     * @throws BracketsParseException throw by JumpInstruction and BackInstruction
     */
    void execute(ArgumentInstruction args) throws MemoryOutOfBoundsException,
            MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException;

    /**
     * Print the short syntax of the command which implement this interface
     */
    void rewrite();

    /**
     * @return String represent the syntax converted to the code color
     */
    String translate();

    void trace(ArgumentInstruction argumentInstruction) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn;
}

