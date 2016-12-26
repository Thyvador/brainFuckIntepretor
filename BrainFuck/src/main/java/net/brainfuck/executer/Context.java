package net.brainfuck.executer;

import java.util.HashMap;
import java.util.Map;

import net.brainfuck.io.BfImageWriter;

/**
 * The Enum Context.
 *
 * @author davidLANG
 */
public enum Context {
	
    /** The check flag. */
    CHECK("--check"),
    UNCHECK("--uncheck"),
    TRANSLATE("--translate"),
    REWRITE("--rewrite"),
    TRACE("--trace");

    public static Map<String, ContextExecuter> contextMap = new HashMap<>();



    private String syntax;
    private ContextExecuter c;

    public static void setExceuter(BfImageWriter writer){
		CHECK.setContextExecuter(new CheckExecuter());
		UNCHECK.setContextExecuter(new UncheckExecuter());
		TRANSLATE.setContextExecuter(new TranslateExecuter(writer));
		REWRITE.setContextExecuter(new RewriteExecuter());
		TRACE.setContextExecuter(new TraceExecuter());

		for (Context c: Context.values()) {
			contextMap.put(c.getSyntax(), c.getContextExecuter());
		}
	}


    /**
	 * Instantiates a new context.
	 *
	 * @param syntax
	 *            the syntax
	 */
    Context(String syntax) {
        this.syntax = syntax;
    }

    /**
	 * Gets the syntax.
	 *
	 * @return the syntax
	 */
    public String getSyntax() { return syntax; }

    /**
	 * Gets the context executer.
	 *
	 * @return the context executer
	 */
    public ContextExecuter getContextExecuter() { return c; }

    /**
	 * Sets the context executer.
	 *
	 * @param c
	 *            the new context executer
	 */
    private void setContextExecuter(ContextExecuter c) { this.c = c; }

}

