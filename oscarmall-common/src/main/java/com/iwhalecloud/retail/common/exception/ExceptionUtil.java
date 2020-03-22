package com.iwhalecloud.retail.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * @author chen.weizheng
 */
public final class ExceptionUtil {
    private ExceptionUtil() { }

    public static final int SQLEXCEPTION_ORA = 1;

    public static final int SQLEXCEPTION_TT = 2;

    public static final int SQLEXCEPTION_ABD = 3;

    public static final int SQLEXCEPTION_QDB = 4;

    public static final int SQLEXCEPTION_ZSMART = 10;

    public static final int SQLEXCEPTION_WHALECLOUD = 11;

    public static final int SQLEXCEPTION_UNKNOWN = 1000;

    public static final int SW_1 = 5;

    public static final int SW_2 = 1024;

    public static Throwable findMostUsefulInner(Throwable t) {
        if (t == null) {
            return null;
        }
        Throwable target = t;
        while (target.getCause() != null) {
            target = target.getCause();
            if (target instanceof Error) {
                return target;
            }
            if (target instanceof SQLException) {
                int type = ExceptionUtil.getSQLExceptionVernderType(target);
                switch (type) {
                    case SQLEXCEPTION_ORA:
                    case SQLEXCEPTION_TT:
                    case SQLEXCEPTION_ZSMART:
                        return target;
                    default:
                }
            }
        }
        return target;
    }

    public static Throwable getMostInnerException(Throwable t) {
        if (t == null) {
            return null;
        }

        Throwable target = t;
        while (target.getCause() != null) {
            target = target.getCause();
        }
        return target;
    }

    public static BaseException getFirstBaseException(Throwable t) {
        Throwable cause = t;
        while (cause != null) {
            if (cause instanceof BaseException) {
                return (BaseException) cause;
            }
            cause = cause.getCause();
        }
        return null;
    }

    public static Object exCheck(Object arg) {
        Object obj = new Object();
        if (arg instanceof Throwable) {
            obj = ExceptionUtil.exToString((Throwable) arg);
        }
        return obj;
    }

    public static String exToString(Throwable t) {
        StringWriter sw = new StringWriter(SW_1 * SW_2);
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        }
        finally {
            pw.close();
        }
    }

    public static int getSQLExceptionVernderType(Throwable t) {
        StackTraceElement[] steList = t.getStackTrace();
        if (steList == null || steList.length <= 0) {
            return SQLEXCEPTION_UNKNOWN;
        }
        String name = steList[0].getClassName();
        if (name == null) {
            return SQLEXCEPTION_UNKNOWN;
        }
        Integer x = getException(name);
        if (x != null) {
            return x;
        }
        return SQLEXCEPTION_UNKNOWN;
    }

    private static Integer getException(String name) {
        String str = name;
        str = str.toLowerCase();
        if (str.startsWith("oracle")) {
            return SQLEXCEPTION_ORA;
        }
        if (str.startsWith("com.timesten")) {
            return SQLEXCEPTION_TT;
        }
        if (str.startsWith("altibase")) {
            return SQLEXCEPTION_ABD;
        }
        if (str.startsWith("com.ztesoft.zsmart.core.jdbc.qdbdriver")) {
            return SQLEXCEPTION_QDB;
        }
        if (str.startsWith("com.ztesoft")) {
            return SQLEXCEPTION_ZSMART;
        }
        if (str.startsWith("com.iwhalecloud")) {
            return SQLEXCEPTION_WHALECLOUD;
        }
        return null;
    }
}
