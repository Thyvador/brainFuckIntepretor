package net.brainfucktest.common;

import net.brainfuck.Main;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test {

    /**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
    public static void main(String[] args) {
        String[] args2 = {"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/test.bf","-i", "-o"};
        new Main(args2); // On lance sur un fichier au hasard
    }
}
