package com.example.okhttp;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/13 20:33
 */
public interface Call {

    Request request();

    Response execute() throws Exception;

    void enqueue(Callback responseCallback);

    void cancel();


    boolean isExecuted();

    boolean isCanceled();

    interface Factory {
        Call newCall(Request request);
    }
}
