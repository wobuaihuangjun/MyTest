package com.example.okhttp.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * 线程相关工具方法
 * Created by zjh on 2017/9/6.
 */

public class ThreadUtil {

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

}
