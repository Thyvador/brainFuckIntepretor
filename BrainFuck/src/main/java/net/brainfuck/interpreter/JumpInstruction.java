package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;

/**
 *  Representation of JUMP instruction "[" "JUMP".
 */
public class JumpInstruction extends AbstractInstruction {

	/**
	 * Instantiates a new jump instruction.
	 */
	public JumpInstruction() {
		super(Language.JUMP);
	}

	/**
	 * Execute the nonLinear approch of JUMP instruction.
	 *
	 * @param argumentInstruction the arguments
	 * @throws MemoryOutOfBoundsException throw by memory
	 * @throws IOException throw by memory
	 */
	@Override
	public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException, IOException {
		nonLinearExecute(argumentInstruction);
	}

	/**
	 * Non linear execute.
	 *
	 * @param argumentInstruction the argument instruction
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void nonLinearExecute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException, IOException {
		Reader reader = argumentInstruction.getReader();

		// Reach corresponding closing bracket
		if (argumentInstruction.getMemory().get() == 0) {
			reader.seek(argumentInstruction.getJumpTable().getAssociated(reader.getExecutionPointer()));
		}
	}
	
	/**
	 * Linear execute.
	 *
	 * @param argumentInstruction the argument instruction
	 * @throws MemoryOutOfBoundsException the memory out of bounds exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BracketsParseException the brackets parse exception
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void linearExecute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		Memory memory = argumentInstruction.getMemory();
		Reader reader = argumentInstruction.getReader();

		if (memory.get() != 0) {
			reader.mark();
		} else {
			int cpt = 1;
			Language instruction;
			while(cpt > 0) {
				instruction = Language.languageMap.get(reader.getNext());
				if (instruction == null) {
					throw new BracketsParseException("]");
				}
				if (instruction == Language.JUMP) {
	                cpt++;
	            } else if (instruction == Language.BACK) {
	            	cpt--;
	            }
			}
			// Reach corresponding closing bracket
		}
	}

}
