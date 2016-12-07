package net.brainfuck.common;

/**
 * @author davidLANG
 */
public final class StringParser {

    private StringParser(){}

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

    public static boolean containsSpace(String str) {
        return str.indexOf(' ') != -1 || str.indexOf('\t') != -1;
    }


    public static String[] getArguments(String str) {
        String tmp = str.substring(str.indexOf("(")+1,str.indexOf(")"));
        return tmp.isEmpty() ? null : tmp.split(",");
    }

    public static boolean isCorrectParentheses(String str) {
        long leftParenthesis  = str.chars().filter(num -> num == '(').count();
        long rightParenthesis= str.chars().filter(num -> num == ')').count();
        return (leftParenthesis == 0 && rightParenthesis == 0) ||
                (leftParenthesis == 1 && rightParenthesis == 1 && str.indexOf('(') < str.indexOf(')'));
    }


    public static boolean containsParenthesis(String str) {
        return str.contains("(")  && str.contains(")");
    }
}
