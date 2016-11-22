package net.brainfuck.interpreter;

import net.brainfuck.common.ArgumentInstruction;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;

// TODO: Auto-generated Javadoc
/**
 * The Class BackInstruction.
 */
public class BackInstruction extends AbstractExecute {

	/**
	 * Instantiates a new back instruction.
	 */
	public BackInstruction() {
		super(Language.BACK);
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.interpreter.InstructionInterface#execute(net.brainfuck.common.ArgumentInstruction)
	 */
	@Override
	public void execute(ArgumentInstruction argumentInstruction) throws BracketsParseException, IOException, MemoryOutOfBoundsException {
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
		
		if (argumentInstruction.getMemory().get() != 0) {
			reader.seek(argumentInstruction.getJumpTable().getAssociated(reader.getExecutionPointer()));
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
		if (argumentInstruction.getMemory().get() != 0) {
			argumentInstruction.getReader().reset();
		} else {
			argumentInstruction.getReader().unmark();
		}
	}

}
