package net.brainfucktest.common;

import net.brainfuck.Main;

/**
 * Created by Alexandre on 05/11/2016.
 */
public class TestTrace {

	public static void main(String[] args) {

		String[] args2 = {"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/yapi.bf", "--trace"};
		new Main(args2); // On lance sur un fichier au hasard
	}
}
