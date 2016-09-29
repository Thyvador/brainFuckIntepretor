package net.brainfuck.common;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.SynthaxeErrorException;
import net.brainfuck.interpretor.Interpretor;

import java.io.IOException;

public class Main {
	
	public Main(String filename) throws FileNotFoundException, java.io.FileNotFoundException {
		Memory m = new Memory();
		Reader r = new BfReader(filename);
		Interpretor i = new Interpretor(m,r);

		try {
			i.interprate();
		} catch (net.brainfuck.exception.IOException e) {
			e.printStackTrace();
		} catch (SynthaxeErrorException e) {
            e.printStackTrace();
        } catch (MemoryOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(m);
        System.exit(0);
	}
	// prepare the executable jar
	public static void main(String[] args) throws FileNotFoundException, java.io.FileNotFoundException {
        if(args.length == 2 && args[1].equals(("-p"))){
            new Main(args[1]);
        }else if(args.length == 0) {

            new Main("BrainFuck\\src\\main\\resources\\assets\\brainfuck\\common\\res.bf");// On lance sur un fichier au hasard
        }
	}
	
}
