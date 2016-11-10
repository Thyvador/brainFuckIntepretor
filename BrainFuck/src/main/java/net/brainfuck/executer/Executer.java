package net.brainfuck.executer;

import net.brainfuck.common.*;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.AbstractExecute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author davidLANG
 */
public class Executer {
    private List<ContextExecuter> contextExecuters = new ArrayList<>();
    private ArgumentExecuter argumentExecuter;

    /**
     * Initialize contextExecuters, memory and reader
     *
     * @param m         Memory
     * @param arguments The long arguments
     * @param r         Reader
     */
    public Executer(List<String> arguments, ArgumentExecuter argumentExecuter) throws IOException {
        this.argumentExecuter = argumentExecuter;

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
            c.execute(i, argumentExecuter);
            //
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
        argumentExecuter.getReader().closeReader();

        int index;
        if ((index = this.contextExecuters.indexOf(Context.contextMap.get(Context.CHECK.getSyntax()))) >= 0) {
            CheckExecuter checkExecuter = (CheckExecuter) this.contextExecuters.get(index);
            if (checkExecuter.getCpt() > 0) {
                throw new BracketsParseException();
            }
        }
        if (this.contextExecuters.indexOf(Context.contextMap.get(Context.TRANSLATE.getSyntax())) >= 0) {
            argumentExecuter.getImageWriter().close();
        }
        if(Logger.isWriterOpen()){
            Logger.closeWriter();
        }
    }

}
