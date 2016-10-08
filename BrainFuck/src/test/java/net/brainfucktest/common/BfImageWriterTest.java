package net.brainfucktest.common;

import java.io.IOException;
import java.util.Random;

import net.brainfuck.common.BfImageWriter;

public class BfImageWriterTest {
	
	private static Random rnd = new Random();

	public static void main(String[] args) {
		try {
			test1();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void test1() throws IOException {
		BfImageWriter wrt = new BfImageWriter("c:/Users/user/Desktop/test.bmp");
		wrt.write("FF0000");
		wrt.write("00FF00");
		wrt.write("0000FF");
		wrt.close();		
	}
	
	private static void test2() throws IOException {
		BfImageWriter wrt = new BfImageWriter("c:/Users/user/Desktop/test.bmp");
		for (int i = 0; i < 1000; i++) {
			wrt.write(rnd.nextInt(0xFFFFFF+1));
		}
		wrt.close();
		
	}

}
