package net.brainfucktest.common;

import net.brainfuck.common.BfImageWriter;
import net.brainfuck.exception.FileNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class TestBfImageWriter {

	private static Random rnd = new Random();

	public static void main(String[] args) {
		try {
			test1();
		} catch (IOException | net.brainfuck.exception.IOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void test1() throws IOException, net.brainfuck.exception.IOException, FileNotFoundException {
		BfImageWriter wrt = new BfImageWriter(new FileOutputStream(new File("c:/Users/user/Desktop/test.bmp")));
		wrt.write("0000FF");
		wrt.write("FFFFFF");
		wrt.write("FFFFFF");
		wrt.close();
	}

	@SuppressWarnings("unused")
	private static void test2() throws IOException, net.brainfuck.exception.IOException, FileNotFoundException {
		BfImageWriter wrt = new BfImageWriter(new FileOutputStream(new File("c:/Users/user/Desktop/test.bmp")));
		for (int i = 0; i < 200; i++) {
			wrt.write(rnd.nextInt(0xFFFFFF + 1));
		}
		wrt.close();

	}

}