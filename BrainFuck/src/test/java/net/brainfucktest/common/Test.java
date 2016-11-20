package net.brainfucktest.common;

import net.brainfuck.Main;

public class Test {

    public static void main(String[] args) {
        String[] args2 = {"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/test.bf", "--translate", "-i", "Brainfuck/src/test/resources/assets/brainfucktest/common/test.bmp"};
        new Main(args2); // On lance sur un fichier au hasard
    }
}
