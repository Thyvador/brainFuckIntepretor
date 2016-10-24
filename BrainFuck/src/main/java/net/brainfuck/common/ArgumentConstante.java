package net.brainfuck.common;


/**
 * Usefull constant for argumentAnalyzer
 * @author davidLANG
 */
public class ArgumentConstante {
    public final static String PATH_SYNTAX = "-p";
    public final static String IN_SYNTAX  = "-i";
    public final static String OUT_SYNTAX  = "-o";
    public final static String REWRITE_SYNTAX = "--rewrite";
    public final static String TRANSLATE_SYNTAX  = "--translate";
    public final static String CHECK_SYNTAX  = "--check";

    public final static int PATH = 0;
    public final static int IN_PATH = 1;
    public final static int OUT_PATH = 2;
    public final static int REWRITE = 0;
    public final static int TRANSLATE = 1;
    public final static int CHECK = 2;
}
