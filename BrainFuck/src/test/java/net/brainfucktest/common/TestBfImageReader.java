package net.brainfucktest.common;

import net.brainfuck.common.*;
import net.brainfuck.exception.*;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.Interpreter;

import static net.brainfuck.common.ArgumentConstante.PATH;

/**
 * Created by Alexandre Hiltcher on 26/10/2016.
 */
public class TestBfImageReader {

    public static void main(String[] args2){
        String[] args = {"-p", "c:/Users/Alexandre/Desktop/test.bmp"};
        if (args.length == 0) {
            System.exit(0);
        }
        try {
            ArgumentAnalyzer a = new ArgumentAnalyzer(args);
            if (a.getArgument(PATH) == null) {
                System.exit(0);
            }
            Memory m = new Memory();
            Reader r = new BfImageReader(a.getArgument(PATH));

            Executer e = new Executer(m, a.getFlags(), r);
            Interpreter i = new Interpreter(r, a, e);
            i.interprate();
            System.out.println(m);
        } catch (IOException | SyntaxErrorException | FileNotFoundException | IncorrectArgumentException e) {
            // Exit code not set
            System.exit(5);
        } catch (MemoryOutOfBoundsException e) {
            System.exit(1);
        } catch (MemoryOverFlowException e){
            System.exit(2);
        } catch (FileNotFoundIn e) {
            System.exit(3);
        } catch (BracketsParseException e) {
            System.exit(4);
        }
        System.exit(0);
    }

}
