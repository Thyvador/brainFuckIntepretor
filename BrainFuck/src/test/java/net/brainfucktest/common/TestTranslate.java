package net.brainfucktest.common;

import net.brainfuck.Main;

// TODO: Auto-generated Javadoc
/**
 * The Class TestTranslate.
 */
public class TestTranslate {
	
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new Main(new String[]{"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/loops.bf",
				"--translate", "-o", "Brainfuck/src/test/resources/assets/brainfucktest/common/image/loops.bmp"});
	}
}
