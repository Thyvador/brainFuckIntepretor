package net.brainfucktest.common;

import java.io.IOException;
import java.util.Random;

import net.brainfuck.common.BfImageWriter;
import net.brainfuck.exception.FileNotFoundException;

public class TestBfImageWriter {
	
	private static Random rnd = new Random();

	public static void main(String[] args) {
		try {
			test1();
		} catch (FileNotFoundException | net.brainfuck.exception.IOException e) {
			e.printStackTrace();
		}
	}

	private static void test1() throws net.brainfuck.exception.IOException, FileNotFoundException {
		BfImageWriter wrt = new BfImageWriter("c:/Users/Alexandre/Desktop/test.bmp");
		wrt.write("ff0000");
		wrt.write("00FF00");
		wrt.write("0000FF");
		wrt.close();		
	}
	
	private static void test2() throws net.brainfuck.exception.IOException, FileNotFoundException {
		BfImageWriter wrt = new BfImageWriter("c:/Users/user/Desktop/test.bmp");
		for (int i = 0; i < 1E8; i++) {
			wrt.write(rnd.nextInt(0xFFFFFF+1));
		}
		wrt.close();
		
	}

}