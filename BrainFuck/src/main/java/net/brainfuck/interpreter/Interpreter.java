package net.brainfuck.interpreter;

import net.brainfuck.common.Logger;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.*;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

import java.util.HashMap;
import java.util.Map;

/**
 * The interpretor of brainfuck.
 * Execute the assiocate instruction to the syntax
 *
 * @author davidLANG
 */

public class Interpreter {
    private Map<String, Language> interpretorExecuter = new HashMap<>();
    private Executer executer;
    private Executable reader;
    private Logger logger;

    /**
     * Constructor which initialize attribute.
     *
     * @param executer the executer
     * @param reader   the reader
     * @throws FileNotFoundException throw by setIo()
     * @throws IOException           throw bt BfImageWriter
     */
    public Interpreter(Executer executer, Executable reader) throws IOException {
        this.executer = executer;
        this.reader = reader;
        this.logger = Logger.getInstance();
    }


    /**
     * Interpret all characters which can be read with the attribute reader.
     *
     * @throws IOException                {@link IOException} if reader throw an exception.
     * @throws SyntaxErrorException       {@link SyntaxErrorException} if an error of syntax is found.
     * @throws MemoryOutOfBoundsException {@link MemoryOutOfBoundsException} if memory throw an exception.
     * @throws MemoryOverFlowException    throw by memory
     * @throws FileNotFoundIn             the file not found in
     * @throws BracketsParseException     throw by executer
     * @throws FileNotFoundException      the file not found exception
     * @throws SegmentationFaultException
     */
    public void interprate() throws IOException, SyntaxErrorException, MemoryOutOfBoundsException,
            MemoryOverFlowException, FileNotFoundIn, BracketsParseException, SegmentationFaultException {
        AbstractInstruction instruction;

        while ((instruction = reader.getNext()) != null) {
            executer.execute(instruction, reader);
        }
        executer.end(reader);
    }


    /**
     * Mark reader.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void markReader() throws IOException {
        reader.mark();
    }

    /**
     * Reset reader.
     *
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws BracketsParseException the brackets parse exception
     */
    void resetReader() throws IOException, BracketsParseException {
        reader.reset();
    }

}

