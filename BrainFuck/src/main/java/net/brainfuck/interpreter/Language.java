package net.brainfuck.interpreter;

import net.brainfuck.common.StringParser;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.interpreter.instruction.AbstractInstruction;
import net.brainfuck.interpreter.instruction.intoutinsruction.InInstruction;
import net.brainfuck.interpreter.instruction.intoutinsruction.OutInstruction;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.BackInstruction;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.JumpInstruction;
import net.brainfuck.interpreter.instruction.moveinstruction.LeftInstruction;
import net.brainfuck.interpreter.instruction.moveinstruction.RightInstruction;
import net.brainfuck.interpreter.instruction.operationinstruction.DecrementInstruction;
import net.brainfuck.interpreter.instruction.operationinstruction.IncrementInstruction;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent the language with his syntax and his corresponding InstructionInterface (wich implements the corresponding method to the
 * syntax).
 *
 * @author davidLANG
 */
public enum Language {
    // Declaration de l'enum
    INCR(null, "+", "INCR", "ffffff", "(*ptr)++;"),
    DECR(null, "-", "DECR", "4b0082", "(*ptr)--;"),
    RIGHT(null, ">", "RIGHT", "0000ff", "ptr++"),
    LEFT(null, "<", "LEFT", "9400d3", "ptr--"),
    IN(null, ",", "IN", "ffff00", "(*ptr) = getchar();"),
    OUT(null, ".", "OUT", "00ff00", "putchar(*ptr)"),
    JUMP(null, "[", "JUMP", "ff7f00", "while(*ptr) {"),
    BACK(null, "]", "BACK", "ff0000", "}");

    public static Map<String, AbstractInstruction> languageMap = new HashMap<>();

    private AbstractInstruction interpreter;
    private String[] aliases;

    /**
     * Instantiates a new language.
     *
     * @param interpreter InstructionInterface corresponding to syntax
     * @param aliases     au moins 2 string : {String shortSyntax, String longSyntax}
     */
    Language(AbstractInstruction interpreter, String... aliases) {
        this.interpreter = interpreter;
        this.aliases = aliases;
    }

    public static void addInstruction(AbstractInstruction interpreter, String aliase){
        languageMap.put(aliase, interpreter);
    }

    public static void setInstructions(InputStreamReader inputStreamReader,
                                       OutputStreamWriter outputStreamWriter) {
        // Set interpretors
        INCR.setInterpreter(new IncrementInstruction());
        DECR.setInterpreter(new DecrementInstruction());
        RIGHT.setInterpreter(new RightInstruction());
        LEFT.setInterpreter(new LeftInstruction());
        IN.setInterpreter(new InInstruction(inputStreamReader));
        OUT.setInterpreter(new OutInstruction(outputStreamWriter));
        JUMP.setInterpreter(new JumpInstruction(null));
        BACK.setInterpreter(new BackInstruction(null));
        // Init language map
        Language[] languages = Language.values();
        for (Language language : languages) {
            // AbstractInstruction interpreter = language.getInterpreter();
            String[] aliases = language.getAliases();
            for (String alias : aliases) {
                languageMap.put(alias, language.interpreter);
            }
        }
    }

    public static void setJumpTabel(ExecutionReader executionReader) {
        Language.JUMP.setInterpreter(new JumpInstruction(executionReader));
        Language.BACK.setInterpreter(new BackInstruction(executionReader));
    }

    /**
     * Gets the aliases.
     *
     * @return the aliases
     */
    private String[] getAliases() {
        return aliases;
    }

    /**
     * Gets the interpreter.
     *
     * @return the interpreter
     */
    public AbstractInstruction getInterpreter() {
        return interpreter;
    }

    /**
     * Sets the interpreter.
     *
     * @param interpreter the new interpreter
     */
    private void setInterpreter(AbstractInstruction interpreter) {
        this.interpreter = interpreter;
    }

    /**
     * Gets the short syntax.
     *
     * @return the short syntax
     */
    public String getShortSyntax() {
        return this.aliases[0];
    }

    /**
     * Gets the color syntax.
     *
     * @return the color syntax
     */
    public String getColorSyntax() {
        return aliases[2];
    }

	public String getCSyntax() {
		return aliases[3];
	}

}
