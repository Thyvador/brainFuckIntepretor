package net.brainfucktest.common;

import net.brainfuck.Main;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * Created by Alexandre Hiltcher on 06/10/2016.
 */
public class BfImageReaderTest {

	public static void main(String[] args) throws FileNotFoundException, IOException, java.io.IOException {
		String[] args2 = {"-p", "Brainfuck/src/test/resources/assets/brainfucktest/common/image/test43.bmp"};
		new Main(args2); // On lance sur un fichier au hasard

	}
}
