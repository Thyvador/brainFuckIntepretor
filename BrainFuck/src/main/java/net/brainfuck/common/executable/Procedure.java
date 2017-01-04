package net.brainfuck.common.executable;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Pair;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.List;

/**
 * The Function class represents the functions that the can use.
 *
 * @author Alexandre HILTCHER
 */
public class Procedure extends Executable {
    private Memory memory;

    /**
     * Constructs a default procedure.
     *
     * @param procedureName the procedure name
     * @param memory        the memory of the program.
     * @throws MemoryOutOfBoundsException
     */
    public Procedure(String procedureName, Memory memory, List<String> argument) {
        super(procedureName, argument);
        this.memory = memory;
    }


    /**
     * Execute the instructions corresponding to the procedure.
     *
     * @param memory the memory
     * @throws MemoryOutOfBoundsException
     * @throws MemoryOverFlowException
     * @throws IOException
     * @throws FileNotFoundIn
     * @throws BracketsParseException
     * @throws SegmentationFaultException
     */
    @Override
    public void execute(Memory memory) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        super.execute(memory);
        closeReader();
    }

    /**
     * Execute the instructions of the procedure and trace the logs.
     *
     * @param memory the memory
     * @param reader the current execution reader.
     * @throws IOException
     * @throws MemoryOutOfBoundsException
     * @throws BracketsParseException
     * @throws MemoryOverFlowException
     * @throws FileNotFoundIn
     * @throws SegmentationFaultException
     */
    @Override
    public void trace(Memory memory, Executable reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn, SegmentationFaultException {
        super.trace(memory, reader);
        closeReader();
    }

    /**
     * @throws BracketsParseException
     * @see Executable
     */
    @Override
    public void closeReader() throws BracketsParseException, MemoryOutOfBoundsException {
        super.closeReader();
        memory.unlock(false);
    }

    /**
     * Generate the string representing the suite of C instructions representing the function.
     *
     * @return the string representing the suite of C instruction.
     */
    @Override
    public String generate() {
        StringBuilder stringBuilder = new StringBuilder().append(String.format("void %s %s {\n\n",
                name, getArgumentString()));
        stringBuilder.append(super.generate());
        return stringBuilder.append("\n}\n\n").toString();
    }
}
