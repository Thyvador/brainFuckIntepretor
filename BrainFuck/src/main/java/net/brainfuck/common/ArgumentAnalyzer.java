package net.brainfuck.common;


import static net.brainfuck.common.ArgumentConstante.*;

import net.brainfuck.exception.IncorrectArgumentException;

/**
 * Analyze the JVM arguments and stock the analyze result
 *
 * @author davidLANG
 */
public class ArgumentAnalyzer {
    private boolean[] flags = new boolean[3];
    private String[] arguments = new String[3];
    private String[]args = null;

    /**
     * Initialize args and analysze all arguments
     *
     * @param args arguments of JVM
     * @throws IncorrectArgumentException Bad arguments : bad syntax, missing argument
     */
    public ArgumentAnalyzer(String[] args) throws IncorrectArgumentException{
        this.args = args;
        this.analyze();
    }

    /**
     * return the arguments asked : you should use constants in ArgumentConstante
     *
     * @param argument Use ArgumentConstant
     * @return argument : the asked argument
     */
    public String getArgument(int argument) {return this.arguments[argument];}

    /**
     * Returns the flags "--something" for example "--translate"
     * @return the flags
     */
    public boolean[] getFlags() {return this.flags;}

    /**
     * Loop all argument and set the array flags and the array arguments
     * @throws IncorrectArgumentException  Bad arguments : bad syntax, missing argument
     */
    private void analyze() throws IncorrectArgumentException{
        int length = args.length;
        for (int i=0; i < length; i++) {
            switch (args[i]) {
                case CHECK_SYNTAX:
                    flags[CHECK] = true;
                    break;
                case REWRITE_SYNTAX:
                    flags[REWRITE] = true;
                    break;
                case TRANSLATE_SYNTAX:
                    flags[TRANSLATE] = true;
                    break;
                case PATH_SYNTAX:
                    this.getDoubleArgument(args, i, PATH);
                    i += 1;
                    break;
                default:
                    throw new IncorrectArgumentException("bad arguments");
            }
        }
    }

    /**
     * Get the argument corresponding to a "--something" for exemple "--path"
     * Throw an exception if the second argument is null.
     *
     * @param args arguments of JVM
     * @param i the current index of analyze()
     * @param position the position in array of arguments where add the argument
     * @throws IncorrectArgumentException missing argument
     */
    private void getDoubleArgument(String[] args, int i, int position) throws IncorrectArgumentException {
        if (i + 1 > args.length) {
            throw new IncorrectArgumentException("missing argument after " + args[i]);
        }
        this.arguments[position] = args[i+1];
    }

}
