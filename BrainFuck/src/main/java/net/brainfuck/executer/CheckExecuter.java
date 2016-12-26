package net.brainfuck.executer;

import net.brainfuck.common.executables.Executable;
import net.brainfuck.common.executables.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.BackInstruction;
import net.brainfuck.interpreter.instruction.InstructionInterface;
import net.brainfuck.interpreter.instruction.jumpbackinstruction.JumpInstruction;

/**
 * Execute the AbstractInstruction command according to the "--trace" context.
 *
 * @author davidLANG
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




