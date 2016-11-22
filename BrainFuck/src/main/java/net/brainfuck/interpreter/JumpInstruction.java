package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;

// TODO: Auto-generated Javadoc
/**
 * The Class JumpInstruction.
 */
public class JumpInstruction extends AbstractInstruction {

	/**
	 * Instantiates a new jump instruction.
	 */
	public JumpInstruction() {
		super(Language.JUMP);
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.interpreter.InstructionInterface#execute(net.brainfuck.common.ArgumentInstruction)
	 */
	@Override
	public void execute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		nonLinearExecute(argumentInstruction);
	}

	/**
	 * Non linear execute.
	 *
	 * @param argumentInstruction
	 *            the argument instruction
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
	 */
	private void nonLinearExecute(ArgumentInstruction argumentInstruction) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		Reader reader = argumentInstruction.getReader();

		if (argumentInstruction.getMemory().get() == 0) {
			reader.seek(argumentInstruction.getJumpTable().getAssociated(reader.getExecutionPointer()));
			// Reach corresponding closing bracket
		}
	}
	
	/**
	 * Linear execute.
	 *
	 * @param argumentInstruction
	 *            the argument instruction
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws BracketsParseException
	 *             the brackets parse exception
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
