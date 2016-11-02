package net.brainfucktest.common;

import java.io.IOException;
import java.util.Random;

import net.brainfuck.common.BfImageWriter;
import net.brainfuck.exception.FileNotFoundException;

public class TestBfImageWriter {
	
	private static Random rnd = new Random();

	public static void main(String[] args) {
		try {
			test2();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (net.brainfuck.exception.IOException e) {
			e.printStackTrace();
		}
	}

	private static void test1() throws IOException, net.brainfuck.exception.IOException, FileNotFoundException {
		BfImageWriter wrt = new BfImageWriter("c:/Users/user/Desktop/test.bmp");
		wrt.write("0000FF");
		wrt.write("FFFFFF");
		wrt.write("FFFFFF");
		wrt.close();		
	}
	
	private static void test2() throws IOException, net.brainfuck.exception.IOException, FileNotFoundException {
		BfImageWriter wrt = new BfImageWriter("./test144.bmp");
		for (int i = 0; i < 2000; i++) {
			wrt.write("FFFFFF");
		}
		wrt.close();
		
	}

}