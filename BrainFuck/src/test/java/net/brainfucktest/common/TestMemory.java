package net.brainfucktest.common;

import net.brainfuck.common.Memory;

public class TestMemory {
	public static void main(String[] args) {
		Memory m = Memory.getInstance();
		try {
			// get 			OK
			// set 			OK
			// toString 	OK
			// Control 		OK
			// right/left 	OK
			for (int i=0; i<3000; i++)
				m.right();
			m.incr();
			System.out.println(m.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
