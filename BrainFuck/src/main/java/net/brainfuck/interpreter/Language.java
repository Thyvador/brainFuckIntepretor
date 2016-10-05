package net.brainfuck.interpreter;

import java.util.Map;

/**
 * Created by davidLANG on 04/10/2016.
 */
public enum Language {
    INCR("INCR"),
    DECR("DECR"),
    RIGHT("RIGHT"),
    LEFT("LEFT");

    private InterpreterInterface interpreter;

    private String[] aliases;

    // Initialisation des action à effectuer du language
    private static  final InterpreterInterface INCR_EXECUTE = new IncremanteExecute();
    private static  final InterpreterInterface DECR_EXECUTE = new DecremanteExecute();
    private static  final InterpreterInterface RIGHT_EXECUTE = new RightExecute();
    private static  final InterpreterInterface LEFT_EXECUTE = new LeftExecute();
    static {
        INCR.setInterpreter(INCR_EXECUTE);
        DECR.setInterpreter(DECR_EXECUTE);
        RIGHT.setInterpreter(DECR_EXECUTE);
        LEFT.setInterpreter(LEFT_EXECUTE);
    }

    private static Map<String, Language> langages;
    static {
        for (Language l : Language.values()) {
            for (String alias : l.aliases) {
                langages.put(alias, l);
            }
        }
    }

    Language(String ... aliases) {
        this.aliases = aliases;
    }

    public static Language valueOfByAlias(String alias) {
        Language l = langages.get(alias);
        if (l == null) {
            throw new IllegalArgumentException(
                    "No enum alias " + Language.class.getCanonicalName() + "." + alias);
        }
        return l;
    }

    private void setInterpreter(final InterpreterInterface interpreter) {
        this.interpreter = interpreter;
    }


}
