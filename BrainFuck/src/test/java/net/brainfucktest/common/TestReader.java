package net.brainfucktest.common;

import net.brainfuck.common.LineReader;
import net.brainfuck.common.Reader;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * Created by francoisMelkonian on 27/09/2016.
 */
public class TestReader {
    public static void main(String[] args) throws FileNotFoundException, java.io.IOException, IOException {

        Reader r;
        r = new LineReader("C:\\Users\\user\\Desktop\\indianwomen.txt");
        while (r.hasNext()) {
            System.out.println(r.getNext());
        }
    }
}
