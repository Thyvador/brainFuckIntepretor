package net.brainfucktest.common;


import net.brainfuck.common.Memory;
import net.brainfuck.exception.MemoryOverFlowException;

public class TestMemory {
	public static void main(String[] args) {
		Memory m = new Memory();
		for (int i = 0; i < 256; i++) {
			try {
				m.incr();
			} catch (MemoryOverFlowException e) {
				e.printStackTrace();
			}
		}
		System.out.println(m);
	}
}
