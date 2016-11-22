package net.brainfuck.executer;

import net.brainfuck.common.ArgumentExecuter;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.BackInstruction;
import net.brainfuck.interpreter.InstructionInterface;
import net.brainfuck.interpreter.JumpInstruction;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckExecuter.
 *
 * @author davidLANG
 */
class CheckExecuter implements ContextExecuter{
        static private int cpt = 0;
        
        /**
		 * Execute the AbstractExecute command according to a context without "--rewrite" and "--check".
		 *
		 * @param i
		 *            the AbstractCommand to execute
		 * @param argumentExecuter
		 *            the argument executer
		 * @throws MemoryOverFlowException
		 *             throw by memory
		 * @throws IOException
		 *             throw by reader
		 * @throws MemoryOutOfBoundsException
		 *             throw by memory
		 * @throws FileNotFoundIn
		 *             throw by reader
		 * @throws BracketsParseException
		 *             throw by JumpInstruction or by BackInstruction
		 */
        @Override
        public void execute(InstructionInterface i, ArgumentExecuter argumentExecuter) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException {
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




