package net.brainfuck.executer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author davidLANG
 */
public enum Context {
    CHECK("--check"),
    UNCHECK("--uncheck"),
    TRANSLATE("--translate"),
    REWRITE("--rewrite"),
    TRACE("--trace");

    static Map<String, ContextExecuter> contextMap = new HashMap<>();

    static {
        CHECK.setContextExecuter(new CheckExecuter());
        UNCHECK.setContextExecuter(new UncheckExecuter());
        TRANSLATE.setContextExecuter(new TranslateExecuter());
        REWRITE.setContextExecuter(new RewriteExecuter());
        TRACE.setContextExecuter(new TraceExecuter());

        for (Context c: Context.values()) {
            contextMap.put(c.getSyntax(), c.getContextExecuter());
        }
    }

    private String syntax;
    private ContextExecuter c;

    Context(String syntax) {
        this.syntax = syntax;
    }

    public String getSyntax() { return syntax; }

    public ContextExecuter getContextExecuter() { return c; }

    public void setContextExecuter(ContextExecuter c) { this.c = c; }

}

