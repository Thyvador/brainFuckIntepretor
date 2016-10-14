package net.brainfuck.interpreter;

import java.util.Map;

/**
 * @author davidLANG
 */
enum Language {
    // Declaration de l'enum
    INCR(new IncremanteExecute(), "+", "INCR", "ffffff"),
    DECR(new DecremanteExecute(), "-", "DECR", "4b0082"),
    RIGHT(new RightExecute(), ">", "RIGHT", "0000ff"),
    LEFT(new LeftExecute(), "<", "LEFT", "9400d3"),
    IN(new InExecute(), ",", "IN", "ffff00"),
    OUT(new OutExecute(), ".", "OUT", "ffff00");
    //JUMP(new JumpExecute(), "[", "JUMP", "ff7f00"),
    //BACK(new backExecute(), "]", "BACK", "ff0000");
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
