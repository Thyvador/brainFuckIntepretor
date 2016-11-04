package net.brainfucktest.common;

import net.brainfuck.Main;
import net.brainfuck.common.*;
import net.brainfuck.exception.*;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.Interpreter;

import static net.brainfuck.common.ArgumentConstante.PATH;

/**
 * Created by Alexandre Hiltcher on 26/10/2016.
 */
public class TestBfImageReader {

    public static void main(String[] args){
        String [] args2 = {"-p", "./TEST44.bmp"};
        new Main(args2);
    }

}
