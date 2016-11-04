package net.brainfuck.executer;

import net.brainfuck.common.BfImageWriter;
import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.BackInstruction;
import net.brainfuck.interpreter.InterpreterInterface;
import net.brainfuck.interpreter.JumpInstruction;

/**
 * @author davidLANG
 */
class CheckExecuter implements ContextExecuter{
        static private int cpt = 0;
        /**
         * Execute the AbstractExecute command according to a context without "--rewrite" and "--check".
         *
         * @param i the AbstractCommand to execute
         * @param m the memory representation
         * @param r the reader
         * @throws MemoryOverFlowException throw by memory
         * @throws IOException throw by reader
         * @throws MemoryOutOfBoundsException throw by memory
         * @throws FileNotFoundIn throw by reader
         * @throws BracketsParseException throw by JumpInstruction or by BackInstruction
         */
        @Override
        public void execute(InterpreterInterface i, Memory m, Reader r, BfImageWriter imageWriter) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException {
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

        int getCpt() {
            return cpt;
        }
    }




