package com.github.robotbrain.helpmod;

import org.apache.logging.log4j.Logger;

public class LogHelper {
    private static Logger s_logger;

    public static void init(Logger modLog) {
        s_logger = modLog;
        i("Initializing HelpMod");
    }

    public static Logger logger() {
        return s_logger;
    }

    public static void i(String m, Object... o) {
        logger().info(getFormat(m, o));
    }

    private static String getFormat(String m, Object[] o) {
        return String.format("[%s]: %s", Reference.MODID, String.format(m, o));
    }

    public static void e(String m, Object... o) {
        logger().error(getFormat(m, o));
    }

    public static void e(String m, Throwable t, Object... o) {
        logger().error(getFormat(m, o), t);
    }

    public static void d(String m, Object... o) {
        i(m, o);
    }
}
