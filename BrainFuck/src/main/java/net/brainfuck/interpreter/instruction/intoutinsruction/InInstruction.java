package net.brainfuck.interpreter.instruction.intoutinsruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.Language;

import java.io.InputStreamReader;


/**
 * Representation of IN instruction "." "IN".
 *
 * @author François Melkonian
 */
public class InInstruction extends InOutInstruction {
    InputStreamReader inReader;

    /**
     * Instantiates a new in instruction.
     */
    public InInstruction(InputStreamReader inReader) {
        super(Language.IN);
        this.inReader = inReader;
    }

    /**
     * Execute "set" method from class Memory.
     *
     * @param memory the memory
     * @throws MemoryOverFlowException    the memory over flow exception
     * @throws MemoryOutOfBoundsException the memory out of bounds exception
     * @throws FileNotFoundIn             the file not found in
     */
    @Override
    public void execute(Memory memory) throws MemoryOverFlowException, MemoryOutOfBoundsException, FileNotFoundIn {
        int value;
        try {
            value = inReader.read();
            if (value == -1) {
                throw new FileNotFoundIn("is finished");
            }
            memory.set(value);
        } catch (java.io.IOException e) {
            throw new FileNotFoundIn("can't be read");
        }
    }

}
