package net.brainfuck.interpreter.processing;

import net.brainfuck.common.Pair;
import net.brainfuck.common.StringParser;
import net.brainfuck.exception.SyntaxErrorException;
import net.brainfuck.interpreter.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a Macro
 *
 * @author FooBar Team
 */
public class Macro {


	private int nbArgument = 0;
	private List<String> argumentsName = new ArrayList<>();
	private List<Pair<List<Language>, String>> currentList = new ArrayList<>();
	private List<Pair<List<Pair<List<Language>, String>>, String>> listesInstructions = new ArrayList<>();


	/**
	 * Constructor
	 */
	Macro() {
		listesInstructions.add(new Pair<>(currentList, null));
	}

	/**
	 * Add an argument in the argument
	 *
	 * @param argument the argument to add
	 * @throws SyntaxErrorException if the argument is already define
	 */
	void addArgument(String argument) throws SyntaxErrorException {
		if (argumentsName.contains(argument)) {
			throw new SyntaxErrorException("Argument already define : " + argument);
		}
		argumentsName.add(argument);
		this.nbArgument += 1;
	}


	/**
	 * Add an list of instruction and his argument.
	 * The argument corresponding to : the amount of execution of the list of instruction
	 *
	 * @param instructions List of instruction to add
	 * @param argument List of argument
	 */
	void addInstructionsArgument(List<Language> instructions, String argument) {
		currentList.add(new Pair<>(instructions, argument));
	}

	/**
	 * Add an macro to be executed in the corresponding macro
	 *
	 * @param macro the macro to be added
	 * @param arguments the argument of the macro
	 * @param nbexecute the amount of time to execute the macro
	 * @throws SyntaxErrorException {@link SyntaxErrorException SyntaxErrorException}
	 */
	void addMacroInstruction(Macro macro, List<String> arguments, int nbexecute) throws SyntaxErrorException {
		for (int i = 0; i < nbexecute; i++) {
			addMacroInstruction(macro, arguments, null);
		}
	}

	/**
	 * Add an macro to be executed in the corresponding macro
	 *
	 * @param macro the macro to be added
	 * @param arguments the arguments of the macro
	 * @param argumentMacro the argument corresponding of the amount of time to execute the macro
	 * @throws SyntaxErrorException {@link SyntaxErrorException}
	 */
	void addMacroInstruction(Macro macro, List<String> arguments, String argumentMacro) throws SyntaxErrorException {
		List<Pair<List<Pair<List<Language>, String>>, String>> macroPairs = macro.listesInstructions;

		currentList = new ArrayList<>();
		listesInstructions.add(new Pair<>(currentList, argumentMacro));
		if (arguments.size() != macro.nbArgument) {
			String error = (arguments.size() > nbArgument) ? "too much argument" : "not enought argument";
			throw new SyntaxErrorException(error);
		}

		String argument;
		int nbExecute;
		for (Pair<List<Pair<List<Language>, String>>, String> pair : macroPairs) {
			for (Pair<List<Language>, String> pairInstruction : pair.getFirst()) {
				argument = pairInstruction.getSecond() != null ? arguments.get(macro.argumentsName.indexOf(pairInstruction.getSecond())) : null;
				if (argument != null && !argumentsName.contains(argument)) {
					if (StringParser.isNumeric(argument)) {
						nbExecute = Integer.parseUnsignedInt(argument);
						for (int i = 0; i < nbExecute; i++)
							currentList.add(new Pair<List<Language>, String>(pairInstruction.getFirst(), null));
					} else {
						throw new SyntaxErrorException("Unrecognyze argument " + argument);

					}
				} else {
					currentList.add(new Pair<List<Language>, String>(pairInstruction.getFirst(), argument));
				}
			}
		}

	}

	/**
	 * Add an list of instruction to be execute by the macro
	 * @param instructions the list of instruction
	 */
	void addInstructions(List<Language> instructions) {
		currentList.add(new Pair<>(instructions, null));
	}

	/**
	 * Write the macro to an list of language corresponding
	 * to the call of macro without argument
	 *
	 * @return the list of language representating the call of the macro without argument
	 * @throws SyntaxErrorException {@link SyntaxErrorException SyntaxErrorException}
	 */
	public List<Language> write() throws SyntaxErrorException {
		return write(null);
	}

	/**
	 * Write the macro to an list of language corresponding
	 * to the call of the macro with a argument
	 * @param values
	 * @return the list of language representating the call of the macro with argument
	 * @throws SyntaxErrorException {@link  SyntaxErrorException SyntaxErrorException }
	 */
	public List<Language> write(List<Integer> values) throws SyntaxErrorException {
		List<Language> macroExecution = new ArrayList<>();
		int  nbExecuteInstructions;

		this.checkArgument(values);

		for (Pair<List<Pair<List<Language>, String>>, String> instructions : listesInstructions) {
			nbExecuteInstructions = instructions.getSecond() == null ? 1 : this.getArgumentValue(values, instructions.getSecond());
			for (int i = 0; i < nbExecuteInstructions; i++) {
				macroExecution.addAll(writePairs(instructions.getFirst(), values));
			}

		}
		return macroExecution;
	}

	/**
	 * Check if the number of values in the list correspondd to the number of argument
	 * @param values the arguments values
	 * @throws SyntaxErrorException {@link SyntaxErrorException SyntaxErrorException}
	 */
	private void checkArgument(List<Integer> values) throws SyntaxErrorException {
		//TODO Potential null
		if ((values == null && nbArgument != 0) || values.size() != nbArgument) {
			String error = (values == null || values.size() > nbArgument) ? "too much argument" : "not enought argument";
			throw new SyntaxErrorException(error);
		}
	}

	/**
	 * Write a list of pairs (transform it in a List of language)
	 *
	 * @param list List of pairs
	 * @param values List of argument values
	 * @return List of language
	 */
	private List<Language> writePairs(List<Pair<List<Language>, String>> list, List<Integer> values) {
		int nbExecute;
		List<Language> instructions = new ArrayList<>();

		for (Pair<List<Language>, String> pair : list) {
			nbExecute = pair.getSecond() == null ? 1 : this.getArgumentValue(values, pair.getSecond());
			for (int j = 0; j < nbExecute; j++) {
				instructions.addAll(pair.getFirst());
			}
		}
		return instructions;
	}

	/**
	 * Get the value (integer) of the correspond argument String
	 *
	 * @param values the list of argument value
	 * @param argument the argument to get the value
	 * @return the value of the argument
	 */
	private int getArgumentValue(List<Integer> values, String argument) {
		return values.get(argumentsName.indexOf(argument));
	}


	/**
	 * Return true if the macro contain the argument in parameter
	 *
	 * @param argument an argument
	 * @return true or false
	 */
	boolean containsArgument(String argument) {
		return argumentsName.contains(argument);
	}


}
