package net.brainfuck.interpreter;

import java.util.Map;

/**
 * @author davidLANG
 */
enum Language {
    INCR(new IncremanteExecute(), "INCR", "+"),
    DECR(new DecremanteExecute(), "DECR","-"),
    RIGHT(new RightExecute(), "RIGHT",">"),
    LEFT(new LeftExecute(), "LEFT","<"),
    OUT(new OutExecute(), "OUT","."),
    IN(new InExecute(), "IN",",");

    private InterpreterInterface interpreter;
    private String[] aliases;

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
}
