package net.brainfucktest.common;

import main.java.net.brainfuck.common.FileReader;
import main.java.net.brainfuck.common.Reader;
import main.java.net.brainfuck.common.exception.FileNotFoundException;
import main.java.net.brainfuck.common.exception.IOException;

/**
 * Created by francoisMelkonian on 27/09/2016.
 */
public class TestReader {
    public static void main(String[] args){
        try {

            Reader r;
            r = new FileReader("C:\\Users\\user\\Desktop\\indianwomen.txt");
            while(r.hasNext()){
                System.out.println(r.getNext());
            }
        }catch (){}
    }
}
