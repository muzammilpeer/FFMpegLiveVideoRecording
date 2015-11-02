package com.muzammilpeer.commandapp.util;

import android.util.Log;

/**
 * Created by muzammilpeer on 11/1/15.
 */
public class Log4a {

    public static boolean ENABLE_LOG = true;
    private static final boolean VERBOSE = true & ENABLE_LOG;
    private static final boolean TEMP = true & ENABLE_LOG;
    private static final boolean WARNING = true & ENABLE_LOG;
    private static final boolean INFO = true & ENABLE_LOG;
    public static boolean DEBUG = true & ENABLE_LOG;
    public static boolean ERROR = true & ENABLE_LOG;

    public static void obvious(String tag, String msg) {
        if (DEBUG) {
            msg = "*********************************\n" + msg
                    + "\n*********************************";
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void d(boolean bool, String tag, String msg) {
        if (TEMP & bool)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (INFO)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (ERROR)
            Log.e(tag, msg);
    }

    public static void e(boolean bool, String tag, String msg) {
        if (TEMP & bool)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (VERBOSE)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (WARNING)
            Log.w(tag, msg);
    }

    public static String getStackTraceString(Exception e) {
        return Log.getStackTraceString(e);
    }

    public static void printException(Exception e) {
        Log4a.e("printException ", e == null || e.getMessage() == null ? "Exception" : e.getMessage());
        Log4a.e("printExceptionStack ", getStackTraceString(e));
    }

    public static void w(String tag, String msg, Exception e) {
        if (WARNING)
            Log.w(tag, msg, e);
    }
}