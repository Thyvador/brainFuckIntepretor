package net.brainfuck.common;

/**
 * @author davidLANG
 */
public final class RegexParser {

    private RegexParser(){}

    /**
     * Checks if is numeric.
     *
     * @param str the string to analyze
     * @return true, if is numeric
     */
    public static boolean isNumeric(String str) {
        return str.matches("^\\d+$");
    }

    public static String[] splitSpace(String str) {return str.split("\\s+"); }
}
