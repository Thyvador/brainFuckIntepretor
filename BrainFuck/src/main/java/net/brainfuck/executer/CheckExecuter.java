package net.brainfuck.executer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundIn;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.interpreter.instruction.InstructionInterface;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.BackInstruction;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.JumpInstruction;

/**
 * Execute the AbstractInstruction command according to the "--trace" context.
 *
 * @author FooBar Team
 */
class CheckExecuter implements ContextExecuter {

	static private int cpt = 0;

	/**
	 * Execute the AbstractInstruction command according to a context without "--rewrite" and "--check".
	 *
	 * @param i      the AbstractCommand to execute
	 * @param memory the argument executer
	 * @throws MemoryOverFlowException    throw by memory
	 * @throws IOException                throw by reader
	 * @throws MemoryOutOfBoundsException throw by memory
	 * @throws FileNotFoundIn             throw by reader
	 * @throws BracketsParseException     throw by JumpInstruction or by BackInstruction
	 */
	@Override
	public void execute(InstructionInterface i, Memory memory, Executable reader) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException {
		if (i instanceof JumpInstruction) {
			cpt++;
		}
		if (i instanceof BackInstruction) {
			cpt--;
			if (cpt < 0) {
				throw new BracketsParseException();
			}
		}
	}

	/**
	 * Gets the cpt.
	 *
	 * @return the cpt
	 */
	int getCpt() {
		return cpt;
	}
}




