package net.brainfucktest.common;

import net.brainfuck.Main;
import net.brainfuck.exception.FileNotFoundException;

/**
 * Created by François Melkonian
 * pour certain le chemin est Brainfuck/src/test/resources/assets/brainfucktest/common/
 * Some test about errors
 */
public class TestErrors {
    public static void main(String[] args) {
        String[] args2 = {"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/syntax/OutOfBoundLeft.bf"};
        new Main(args2);
        //new Main("Brainfuck/src/test/resources/assets/brainfucktest/common/OutOfBoundRight.bf");
        // new Main("Brainfuck/src/test/resources/assets/brainfucktest/common/OverflowDecr.bf");
        // new Main("Brainfuck/src/test/resources/assets/brainfucktest/common/OverflowIncr.bf");
    }

}
