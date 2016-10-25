package net.brainfuck.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent the language with his syntax and his corresponding InterpreterInterface (wich implements the corresponding method to the
 * syntax)
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
	OUT(null, ".", "OUT", "ffff00"),
	JUMP(null, "[", "JUMP", "ff7f00"),
	BACK(null, "]", "BACK", "ff0000");

	static Map<String, Language> languageMap = new HashMap<>();
	
	static {
		// Set interpretors
		INCR.setInterpreter(new IncremanteExecute());
		DECR.setInterpreter(new DecremanteExecute());
		RIGHT.setInterpreter(new RightExecute());
		LEFT.setInterpreter(new LeftExecute());
		IN.setInterpreter(new InExecute());
		OUT.setInterpreter(new OutExecute());
		JUMP.setInterpreter(new JumpExecute());
		BACK.setInterpreter(new BackExecute());
		// Init language map
		Language[] languages = Language.values();
		for (Language language : languages) {
			// AbstractExecute interpreter = language.getInterpreter();
			String[] aliases = language.getAliases();
			for (String alias : aliases) {
				languageMap.put(alias, language);
			}
		}
	}

	private AbstractExecute interpreter;
	private String[] aliases;

	/**
	 *
	 * @param interpreter
	 *            InterpreterInterface corresponding to syntax
	 * @param aliases
	 *            au moins 2 string : {String shortSyntax, String longSyntax}
	 */
	Language(AbstractExecute interpreter, String... aliases) {
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
	public AbstractExecute getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(AbstractExecute interpreter) {
		this.interpreter = interpreter;
	}

	/**
	 * 
	 * @return the short syntax
	 */
	public String getShortSyntax() {
		return this.aliases[0];
	}

	public String getColorSyntax() {
		return aliases[2];
	}

}
