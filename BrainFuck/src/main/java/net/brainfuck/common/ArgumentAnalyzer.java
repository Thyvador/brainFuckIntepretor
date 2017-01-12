package net.brainfuck.common;


import net.brainfuck.exception.IncorrectArgumentException;
import net.brainfuck.executer.Context;

import java.util.ArrayList;
import java.util.List;

import static net.brainfuck.common.ArgumentConstante.*;


/**
 * Analyze the JVM arguments and stock the analyze result.
 *
 * @author FoobarTeam
 */
public class ArgumentAnalyzer {
    private String[] arguments = new String[3];
    private List<String> flags = new ArrayList<>();
    private String[] args = null;

    /**
     * Initialize args and analyse all arguments.
     *
     * @param args arguments of JVM
     * @throws IncorrectArgumentException Bad arguments : bad syntax, missing argument
     */
    public ArgumentAnalyzer(String[] args) throws IncorrectArgumentException {
        this.args = args;
        this.analyze();
    }

    /**
     * Return the argument.
     *
     * @param argument the index of the argument.
     * @return the string corresponding to the argument.
     */
    public String getArgument(int argument) {
        return this.arguments[argument];
    }

    /**
     * Returns the flags list.
     *
     * @return the flags
     */
    public List<String> getFlags() {
        return this.flags;
    }

    /**
     * Loop through all arguments and set the array flags and the array arguments.
     *
     * @throws IncorrectArgumentException Bad arguments : bad syntax, missing argument
     */
    private void analyze() throws IncorrectArgumentException {
        int length = args.length;
        for (int i = 0; i < length; i++) {
            switch (args[i]) {
                case PATH_SYNTAX:
                    this.getDoubleArgument(args, i, PATH);
                    i += 1;
                    break;
                case IN_SYNTAX:
                    this.getDoubleArgument(args, i, IN_PATH);
                    i += 1;
                    break;
                case OUT_SYNTAX:
                    this.getDoubleArgument(args, i, OUT_PATH);
                    i += 1;
                    break;
                default:
                    this.analyzeLongArgument(args[i]);
                    break;
            }
        }
    }

    /**
     * If the argument is correct add it to List<String> flags, throw an error otherwise.
     *
     * @param longArgument the argument to check.
     * @throws IncorrectArgumentException Throw when the argument is incorrect
     */
    private void analyzeLongArgument(String longArgument) throws IncorrectArgumentException {
        boolean error = true;
        for (Context c : Context.values()) {
            if (c.getSyntax().equals(longArgument)) {
                flags.add(c.getSyntax());
                error = false;
            }
        }
        if (error) {
            throw new IncorrectArgumentException(longArgument);
        }
    }

    /**
     * Get the argument corresponding to previous argument. Throw an exception if the second argument is null.
     *
     * @param args     arguments of JVM
     * @param i        the current index of analyze()
     * @param position the position in array of arguments where add the argument
     * @throws IncorrectArgumentException missing argument
     */
    private void getDoubleArgument(String[] args, int i, int position) throws IncorrectArgumentException {
        if (i + 1 >= args.length) {
            throw new IncorrectArgumentException("missing argument after " + args[i]);
        }
        this.arguments[position] = args[i + 1];
    }

}
