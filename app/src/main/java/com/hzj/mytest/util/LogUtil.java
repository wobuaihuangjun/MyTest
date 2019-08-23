package com.hzj.mytest.util;

import android.support.annotation.NonNull;
import android.util.Log;

public class LogUtil {
    public static void v(String message) {
        Log.v(":", message);
    }

    public static void d(String message) {
        Log.d(":", message);
    }

    public static void i(String message) {
        Log.i(":", message);
    }

    public static void w(String message) {
        Log.w(":", message);
    }

    public static void e(String message) {
        Log.e(":", message);
    }

    public static void wtf(String message) {
        Log.wtf(":", message);
    }

    public static void e(Throwable throwable) {
        Log.e(":", "", throwable);
    }

    public static void v(String tag, String message) {
        Log.v(tag, message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        Log.i(tag, message);
    }

    public static void w(String tag, String message) {
        Log.w(tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void wtf(String tag, String message) {
        Log.wtf(tag, message);
    }

    public static void w(String tag, Throwable throwable) {
        Log.w(tag, throwable);
    }

    public static void e(String tag, Throwable throwable) {
        Log.e(tag, "", throwable);
    }

    public static void wtf(@NonNull String tag, @NonNull Throwable cause) {
        Log.wtf(tag, cause);
    }

    public static void v(String tag, String message, Throwable throwable) {
        Log.v(tag, message, throwable);
    }

    public static void d(String tag, String message, Throwable throwable) {
        Log.d(tag, message, throwable);
    }

    public static void i(String tag, String message, Throwable throwable) {
        Log.i(tag, message, throwable);
    }

    public static void w(String tag, String message, Throwable throwable) {
        Log.w(tag, message, throwable);
    }

    public static void e(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    public static void wtf(String tag, String message, Throwable throwable) {
        Log.wtf(tag, message, throwable);
    }

}
