package net.brainfuck.interpreter.instruction;

import net.brainfuck.common.ExecutionReader;
import net.brainfuck.common.Memory;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

/**
 * Representation of JUMP instruction "[" "JUMP".
 */
public class JumpInstruction extends AbstractInstruction {
	JumpTable jumpTable;

	/**
	 * Instantiates a new jump instruction.
	 */
	public JumpInstruction(JumpTable jumpTable) {
		super(Language.JUMP);
		this.jumpTable = jumpTable;
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
	public void execute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		linearExecute(memory, reader);
	}

	private void linearExecute(Memory memory, ExecutionReader reader) throws MemoryOutOfBoundsException, IOException, BracketsParseException {
		if (memory.get() != 0) {
			reader.mark();
		} else {
			int cpt = 1;
			Language instruction;
			while (cpt > 0) {
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

	private void nonLinearExecute(Memory memory, ExecutionReader reader)
			throws MemoryOutOfBoundsException, IOException {
		// Reach corresponding closing bracket
		if (memory.get() == 0) {
			reader.seek(jumpTable.getAssociated(reader.getExecutionPointer()));
		}
	}

}
