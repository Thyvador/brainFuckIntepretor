package net.brainfuck.interpreter;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * Represent the language with his syntax and his corresponding InstructionInterface (wich implements the corresponding method to the
 * syntax).
 *
 * @author davidLANG
 */
enum Language {
	// Declaration de l'enum
	INCR(null, "+", "INCR", "ffffff"),
	DECR(null, "-", "DECR", "4b0082"),
	RIGHT(null, ">", "RIGHT", "0000ff"),
	LEFT(null, "<", "LEFT", "9400d3"),
	IN(null, ",", "IN", "ffff00"),
	OUT(null, ".", "OUT", "00ff00"),
	JUMP(null, "[", "JUMP", "ff7f00"),
	BACK(null, "]", "BACK", "ff0000");

	static Map<String, Language> languageMap = new HashMap<>();
	
	static {
		// Set interpretors
		INCR.setInterpreter(new IncrementInstruction());
		DECR.setInterpreter(new DecrementInstruction());
		RIGHT.setInterpreter(new RightInstruction());
		LEFT.setInterpreter(new LeftInstruction());
		IN.setInterpreter(new InInstruction());
		OUT.setInterpreter(new OutInstruction());
		JUMP.setInterpreter(new JumpInstruction());
		BACK.setInterpreter(new BackInstruction());
		// Init language map
		Language[] languages = Language.values();
		for (Language language : languages) {
			// AbstractInstruction interpreter = language.getInterpreter();
			String[] aliases = language.getAliases();
			for (String alias : aliases) {
				languageMap.put(alias, language);
			}
		}
	}

	private AbstractInstruction interpreter;
	private String[] aliases;

	/**
	 * Instantiates a new language.
	 *
	 * @param interpreter
	 *            InstructionInterface corresponding to syntax
	 * @param aliases
	 *            au moins 2 string : {String shortSyntax, String longSyntax}
	 */
	Language(AbstractInstruction interpreter, String... aliases) {
		this.interpreter = interpreter;
		this.aliases = aliases;
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
	 * @param interpreter
	 *            the new interpreter
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

}
