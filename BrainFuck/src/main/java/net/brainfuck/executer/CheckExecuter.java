package net.brainfuck.executer;

import net.brainfuck.common.Memory;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.*;
import net.brainfuck.interpreter.AbstractExecute;
import net.brainfuck.interpreter.BackExecute;
import net.brainfuck.interpreter.JumpExecute;

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
         * @throws BracketsParseException throw by JumpExecute or by BackExecute
         */
        @Override
        public void execute(AbstractExecute i, Memory m, Reader r) throws MemoryOverFlowException, IOException, MemoryOutOfBoundsException, FileNotFoundIn, BracketsParseException {
            if (i instanceof JumpExecute) {
                cpt++;
            }
            if (i instanceof BackExecute) {
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




