package net.brainfucktest.common;

import net.brainfuck.common.BfImageReader;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * Created by Alexandre Hiltcher on 06/10/2016.
 */
public class BfImageReaderTest {

    public static void main(String[] args) throws FileNotFoundException, IOException, java.io.IOException {
        BfImageReader bfImageReader = new BfImageReader("BrainFuck/src/test/resources/assets/brainfucktest/common/test43.bmp");
        String nb;
        while((nb=bfImageReader.getNext()) != null) {
            System.out.println(nb);
        }

    }
}
