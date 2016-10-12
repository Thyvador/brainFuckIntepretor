package net.brainfuck.interpreter;

import java.util.Map;

/**
 * @author davidLANG
 */
enum Language {
    // Declaration de l'enum
    INCR(new IncremanteExecute(), "+", "INCR"),
    DECR(new DecremanteExecute(), "-", "DECR"),
    RIGHT(new RightExecute(), ">", "RIGHT"),
    LEFT(new LeftExecute(), "<", "LEFT"),
    IN(new RightExecute(), ",", "IN"),
    OUT(new LeftExecute(), ".", "OUT");

    private InterpreterInterface interpreter;
    private String[] aliases;

    /**
     *
     * @param interpreter InterpreterInterface corresponding to syntax
     * @param aliases au moins 2 string : {String shortSyntax, String longSyntax}
     */
    Language(InterpreterInterface interpreter, String ... aliases) {
        this.interpreter = interpreter;
        this.aliases = aliases;
    }

    public String[] getAliases() {
        return aliases;
    }

    public InterpreterInterface getInterpreter() {
        return interpreter;
    }

    public String   getShortSyntax() {
        return this.aliases[0];
    }
}
