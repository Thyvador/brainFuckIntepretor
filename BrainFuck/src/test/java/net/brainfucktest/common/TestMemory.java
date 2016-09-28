package net.brainfucktest.common;


import net.brainfuck.common.Memory;

public class TestMemory {
	public static void main(String[] args) {
		Memory m = Memory.getInstance();
		try {
			m.left();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
