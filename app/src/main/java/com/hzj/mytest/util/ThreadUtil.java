package com.hzj.mytest.util;

import android.os.Looper;

/**
 * 线程工具
 * <p>
 * Created by hzj on 2016/12/2.
 */
public class ThreadUtil {

    public ThreadUtil() {
    }

    public static void throwExceptionInMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalThreadStateException("Illegal use the main thread !");
        }
    }
}
