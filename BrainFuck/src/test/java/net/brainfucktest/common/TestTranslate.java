package net.brainfucktest.common;

import net.brainfuck.Main;

public class TestTranslate {
	public static void main(String[] args) {
		new Main(new String[] { "-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/image/fractales.bf",
				"--translate", "-o", "c:/Users/user/Desktop/translate.bmp"});
	}
}
