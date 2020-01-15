package com.example.okhttp;

import java.io.IOException;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/14 17:47
 */
public interface Interceptor {

    Response intercept(Chain chain) throws IOException;

    interface Chain {
        Request request();

        Response proceed(Request request) throws IOException;
    }

}
