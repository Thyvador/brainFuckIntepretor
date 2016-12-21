package net.brainfuck.common.executables;

import net.brainfuck.common.Memory;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.interpreter.JumpTable;
import net.brainfuck.interpreter.Language;

import java.util.List;

/**
 * Created by thyvador on 21/12/16.
 */
public class Procedures extends Executables {

    public Procedures(List<Language> instructions, JumpTable jumpTable, Memory memory) {
        super(instructions, jumpTable);
        openScope(memory);
    }

    private void openScope(Memory memory) {
        // TODO: 21/12/16 Add memmory.sopce() 
    }

    @Override
    public void closeReader() throws BracketsParseException {
        super.closeReader();
        // TODO: 21/12/16 Add memory.scopeclose
    }
}
