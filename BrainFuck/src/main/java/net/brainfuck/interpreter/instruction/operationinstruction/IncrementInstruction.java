package net.brainfuck.interpreter.instruction.operationinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.Language;


/**
 * Representation of Back instruction "+" "INCR".
 *
 * @author davidLANG
 */
public class IncrementInstruction extends DecrementIncrementInstruction {

    /**
     * Instantiates a new incremante instruction.
     */
    public IncrementInstruction() {
        super(Language.INCR);
    }

    /**
     * Execute the "incr" method of Memory Class.
     *
     * @param memory the memory
     * @throws MemoryOverFlowException    the memory over flow exception
     * @throws MemoryOutOfBoundsException the memory out of bounds exception
     */
    @Override
    public void execute(Memory memory) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        memory.incr();
    }

}
