package net.brainfucktest.common;

import net.brainfuck.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author user
 * @date 06/11/2016
 */
public class TestChooser {
	public static void main(String[] argumentsDuMain) throws IOException {
		System.out.println("Which test do you want to execute ?");
		File dir = new File("Brainfuck/src/test/resources/assets/brainfucktest/common");
		List<File> files = Arrays.asList(dir.listFiles());
		for (int i = 0; i < files.size(); i++) {
			System.out.println(i+"\t"+files.get(i).getName());
		}
		int chooseCalculated = 0;
		int choose;
		while ((choose = System.in.read())!='\n'){
			chooseCalculated = chooseCalculated * 10 + (choose - '0');
		}
		System.out.println("bfck.sh -p "+files.get(chooseCalculated).getName()+" [ARGS]");
		System.out.print("ARGS = ");
		String args ="-p "+files.get(chooseCalculated)+" ";
		while ((choose = System.in.read())!='\n'){
			args += Character.toString((char)choose);
		}
		System.out.println("bfck.sh -p "+args);
		new Main(args.split(" "));
	}
}
