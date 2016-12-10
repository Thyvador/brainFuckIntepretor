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
        String[] res = null;
        if (isCorrectParentheses(str) && containsParenthesis(str)) {
            String tmp = str.substring(str.indexOf("(")+1,str.indexOf(")"));
            if (!tmp.isEmpty()) {
                res = tmp.split(",");
                for (int i=0; i < res.length; i++) {
                    res[i] = res[i].trim();
                }
            }

            //res = tmp.isEmpty() ? null : tmp.split(",");
        }

        return res;
    }

    public static boolean isCorrectParentheses(String str) {
        long leftParenthesis  = str.chars().filter(num -> num == '(').count();
        long rightParenthesis= str.chars().filter(num -> num == ')').count();
        return (leftParenthesis == 0 && rightParenthesis == 0) ||
                (leftParenthesis == rightParenthesis  && str.indexOf('(') < str.indexOf(')'));
    }


    public static boolean containsParenthesis(String str) {
        return str.contains("(")  && str.contains(")");
    }
}
