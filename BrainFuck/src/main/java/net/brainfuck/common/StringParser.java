package net.brainfuck.common;

/**
 * @author FoobarTeam
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

    /**
     * Split string when space is encountered
     * @param str the line to split
     * @return an array with text
     */
    public static String[] splitSpace(String str) {return str.split("\\s+"); }

    /**
     * Check if the string contains a space
     * @param str the string to check
     * @return true if str contains a space
     */
    public static boolean containsSpace(String str) {
        return str.indexOf(' ') != -1 || str.indexOf('\t') != -1;
    }

    /**
     * Get all arguments between parenthesis
     * @param str the string with arguments
     * @return an array of arguments
     */
    public static String[] getArguments(String str) {
        String[] res = null;
        if (isCorrectParentheses(str) && containsParenthesis(str)) {
            String tmp = str.substring(str.indexOf("(")+1,str.indexOf(")"));
            if (!tmp.isEmpty()) {
                res = tmp.split("\\s*,\\s*");
                for (int i=0; i < res.length; i++) {
                    res[i] = res[i].trim();
                }
            }
        }
        return res;
    }

    /**
     * check if a string has correct parenthesis
     * @param str
     * @return
     */
    public static boolean isCorrectParentheses(String str) {
        long leftParenthesis  = str.chars().filter(num -> num == '(').count();
        long rightParenthesis= str.chars().filter(num -> num == ')').count();
        return (leftParenthesis == 0 && rightParenthesis == 0) ||
                (leftParenthesis == rightParenthesis  && str.indexOf('(') < str.indexOf(')'));
    }

    /**
     * Check if a string contains a parenthesis open and close
     * @param str the string to check
     * @return true if it contains
     */
    public static boolean containsParenthesis(String str) {
        return str.contains("(")  && str.contains(")");
    }
}
