package net.brainfucktest.common;

import net.brainfuck.Main;


/**
 * @author davidLANG
 */
public class TestComment {

    public static void main(String[] args) {
        String[] args2 = {"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/Comment.bf"};
        new Main(args2); // On lance sur un fichier au hasard
    }
}
