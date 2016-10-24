package net.brainfuck.interpreter;

/**
 * Represent the language with his syntax and his corresponding InterpreterInterface
 * (wich implements the corresponding method to the syntax)
 *
 * @author davidLANG
 */
enum Language {
    // Declaration de l'enum
    INCR(new IncremanteExecute(), "+", "INCR", "ffffff"),
    DECR(new DecremanteExecute(), "-", "DECR", "4b0082"),
    RIGHT(new RightExecute(), ">", "RIGHT", "0000ff"),
    LEFT(new LeftExecute(), "<", "LEFT", "9400d3");
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

    /**
     * @return the aliases
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * 
     * @return the interpreter
     */
    public InterpreterInterface getInterpreter() {
        return interpreter;
    }

    /**
     * 
     * @return the short syntax
     */
    public String   getShortSyntax() {
        return this.aliases[0];
    }
}
