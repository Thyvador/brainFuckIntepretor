package net.brainfucktest.common;

import net.brainfuck.Main;

/**
 * Created by François Melkonian
 * pour certain le chemin est Brainfuck/src/test/resources/assets/brainfucktest/common/
 * Some test about errors
 */
public class TestErrors {
	public static void main(String[] args) {
		String[] args2 = {"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/syntax/OutOfBoundLeft.bf"};
		new Main(args2);
	}

}
