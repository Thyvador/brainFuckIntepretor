package net.brainfuck.executer;

import net.brainfuck.common.BfImageWriter;
import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.AbstractExecute;
import net.brainfuck.interpreter.InterpreterInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author davidLANG
 */
public class Executer {
    private List<ContextExecuter> contextExecuters = new ArrayList<>();
    private Memory memory;
    private Reader reader;
    private BfImageWriter imageWriter;
    private String fileName;

    /**
     * Initialize contextExecuters, memory and reader
     *
     * @param m         Memory
     * @param arguments The long arguments
     * @param r         Reader
     * @param fileName  String
     */
    public Executer(Memory m, List<String> arguments, Reader r, String fileName) {
        this.memory = m;
        this.reader = r;
        this.fileName = fileName;

        // Initialize context executer
        this.contextExecuters.add(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        if (arguments.size() > 0) {
            this.contextExecuters.remove(Context.contextMap.get(Context.UNCHECK.getSyntax()));
        }
        for (String argument : arguments) {
            this.contextExecuters.add(Context.contextMap.get(argument));
        }
    }

    /**
     * Execute the AbstractExecute command according to the context.
     *
     * @param i AbstractExecute command to execute
     * @throws MemoryOutOfBoundsException Throw by memory
     * @throws BracketsParseException     Throw by Interpreter
     * @throws MemoryOverFlowException    Throw by memory
     * @throws FileNotFoundIn             Throw by reader
     * @throws IOException                Throw by reader
     */
    public void execute(AbstractExecute i) throws MemoryOutOfBoundsException, BracketsParseException,
            MemoryOverFlowException, FileNotFoundIn, IOException {
        for (ContextExecuter c : contextExecuters) {
            c.execute(i, memory, reader, imageWriter, fileName);
        }
    }

    /**
     * This function must be called when all instruction have been read and execute
     * She throw an error if the program has no enought parenthesis
     * She close the Reader.*
     * She close the imageWriter if the long argument "--translate" have been passed
     *
     * @throws BracketsParseException throw if the program have more "[" than "]"
     * @throws IOException            throw by reader.closeReader() and imageWrite.close()
     * @throws FileNotFoundException  throw by reader.closeReader() and imageWrite.close()
     */
    public void end() throws BracketsParseException, IOException, FileNotFoundException {
        reader.closeReader();

        int index;
        if ((index = this.contextExecuters.indexOf(Context.contextMap.get(Context.CHECK.getSyntax()))) >= 0) {
            CheckExecuter checkExecuter = (CheckExecuter) this.contextExecuters.get(index);
            if (checkExecuter.getCpt() > 0) {
                throw new BracketsParseException();
            }
        }
        if (this.contextExecuters.indexOf(Context.contextMap.get(Context.TRANSLATE.getSyntax())) >= 0) {
            imageWriter.close();
        }
        if(Logger.isWriterOpen()){
            Logger.closeWriter();
        }
    }

    /**
     * Set imageWriter parameter
     *
     * @param i imageWriter
     */
    public void setImageWriter(BfImageWriter i) {
        this.imageWriter = i;
    }

}
