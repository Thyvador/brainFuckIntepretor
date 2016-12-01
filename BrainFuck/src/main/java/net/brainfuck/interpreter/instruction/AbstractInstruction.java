package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Logger;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.Language;

/**
 * Abstraction of a instruction
 */
public abstract class AbstractInstruction implements InstructionInterface {

	private Language languageInstr;

	/**
	 * Instantiates a new abstract execute.
	 *
	 * @param languageInstr the language instr
	 */
	AbstractInstruction(Language languageInstr) {
		this.languageInstr = languageInstr;
	}

	/**
	 * Print the short syntax of the command which implement this interface.
	 */
	public final void rewrite() {
		System.out.print(languageInstr.getShortSyntax());
	}

	/**
	 * Return the color (in hexa) which represent the instruction
	 *
	 * @return String hexa wich represent the color of the current instruction
	 */
	public final String translate() {
		return languageInstr.getColorSyntax();
	}

	/**
	 * Execute the instruction and write the trace.
	 *
	 * @param memory the memory
	 * @throws IOException                throw by inReader
	 * @throws MemoryOutOfBoundsException throw by memory
	 * @throws BracketsParseException     throw by interpreter
	 * @throws MemoryOverFlowException    throw by memory
	 * @throws FileNotFoundIn             throw by writer
	 */
	public final void trace(Memory memory, ExecutionReader reader) throws IOException, MemoryOutOfBoundsException, BracketsParseException, MemoryOverFlowException, FileNotFoundIn {
		execute(memory, reader);
		Logger.getInstance().write(reader.getExecutionPointer(), memory);
	}

	public abstract void execute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, MemoryOverFlowException, IOException, FileNotFoundIn, BracketsParseException;
}
