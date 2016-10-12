package net.brainfucktest.common;

import net.brainfuck.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author François Melkonian
 *
 * Test about IN_PATH instruction
 */
public class TestIn {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("Brainfuck/src/test/resources/assets/brainfucktest/common/temp"));
        //new Main("Brainfuck/src/test/resources/assets/brainfucktest/common/TestIn.bf");

    }
}
