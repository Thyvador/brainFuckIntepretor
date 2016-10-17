package net.brainfuck;


import net.brainfuck.exception.IncorrectArgumentException;

import static net.brainfuck.ArgumentConstante.*;

/**
 * @author davidLANG
 */
public class ArgumentAnalyzer {
    private boolean[] flags = {false, false, false};
    private String[] arguments = {null, null, null};
    private String[]    args = null;

    public ArgumentAnalyzer(String[] args) throws IncorrectArgumentException{
        this.args = args;
        this.analyze();
    }

    public String getArgument(int argument) {return this.arguments[argument];}

    public boolean[] getFlags() {return this.flags;}

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

    private void getDoubleArgument(String[] args, int i, int position) throws IncorrectArgumentException {
        if (i + 1 > args.length) {
            // TO DOO
            // TO DOO
            // TO DOO
            throw new IncorrectArgumentException("bad arguments");
        }
        this.arguments[position] = args[i+1];
    }

}
