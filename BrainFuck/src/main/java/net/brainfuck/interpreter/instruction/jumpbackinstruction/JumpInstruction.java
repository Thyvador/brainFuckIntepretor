package net.brainfuck.interpreter.instruction.jumpbackinstruction;

import net.brainfuck.common.Memory;
import net.brainfuck.common.executable.Executable;
import net.brainfuck.common.executable.ExecutionReader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.Language;
import net.brainfuck.interpreter.instruction.AbstractInstruction;

/**
 * Representation of JUMP instruction "[" "JUMP".
 */
public class JumpInstruction extends JumpBackInstruction {
	/**
	 * Instantiates a new jump instruction.
	 * @param executionReader
	 */
	public JumpInstruction(Executable executionReader) {
		super(Language.JUMP, executionReader);
	}

	/**
	 * Execute the nonLinear approch of JUMP instruction.
	 *
	 * @param memory
	 *            the memory
	 * @throws MemoryOutOfBoundsException
	 *             throw by memory
	 * @throws IOException
	 *             throw by memory
	 * @throws BracketsParseException 
	 */
	@Override
	public void execute(Memory memory) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		nonLinearExecute(memory);
	}

	@SuppressWarnings("unused")
	private void linearExecute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		if (memory.get() != 0) {
			reader.mark();
		} else {
			int cpt = 1;
			AbstractInstruction instruction;
			while (cpt > 0) {
				instruction = reader.getNext();
				if (instruction == null) {
					throw new BracketsParseException("]");
				}
				if (instruction.equals(Language.JUMP.getInterpreter())) {
					cpt++;
				} else if (instruction.equals(Language.BACK.getInterpreter())) {
					cpt--;
				}
			}
			// Reach corresponding closing bracket
		}
	}

	private void nonLinearExecute(Memory memory)
			throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		// Reach corresponding closing bracket
		if (memory.get() == 0) {
			reader.seek();
		}
	}

}
