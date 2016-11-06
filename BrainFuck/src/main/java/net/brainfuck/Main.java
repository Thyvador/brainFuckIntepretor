package net.brainfuck;

import net.brainfuck.common.*;
import net.brainfuck.exception.*;
import net.brainfuck.executer.Executer;
import net.brainfuck.interpreter.Interpreter;

import static net.brainfuck.common.ArgumentConstante.PATH;


public class Main {

    /**
     * Print the usage
     */
    private void printUsage() {
        System.out.println("Usage : bfck.sh -p FILE [--rewrite] [--translate] [--check] [-o output_file] [-i input_file]");
    }

    /**
     * Default constructor
     *
     * @param args
     */
    public Main(String[] args) {
        if (args.length == 0) {
            this.printUsage();
            System.exit(0);
        }
        try {
	        Logger.startExecTime();
            ArgumentAnalyzer a = new ArgumentAnalyzer(args);
            if (a.getArgument(PATH) == null) {
                this.printUsage();
                System.exit(0);
            }
            Memory m = new Memory();
            Reader r = null;
            if (a.getArgument(PATH).endsWith(".bmp")) {
                r = new BfImageReader(a.getArgument(PATH));
            } else {
                r = new BfReader(a.getArgument(PATH));
            }
            Executer e = new Executer(m, a.getFlags(), r);
            Interpreter i = new Interpreter(r, a, e);
            i.interprate();
            System.out.print(m);
            System.out.println(Logger.showResume());
        } catch (IOException | SyntaxErrorException | FileNotFoundException | IncorrectArgumentException e) {
            // Exit code not set
            System.exit(5);
        } catch (MemoryOutOfBoundsException e) {
            System.exit(1);
        } catch (MemoryOverFlowException e) {
            System.exit(2);
        } catch (FileNotFoundIn e) {
            System.exit(3);
        } catch (BracketsParseException e) {
            System.exit(4);
        }
        System.exit(0);
    }

    /**
     * @param args command-line args
     */
    public static void main(String[] args) {
        new Main(args);
    }

}
