package com.example.okhttp;

import java.io.IOException;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/15 16:40
 */
public class TestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("TestInterceptor.intercept()");
        Request request = chain.request();
        Response response = chain.proceed(request);
        System.out.println("TestInterceptor.intercept(), request.data:" + request.getData());
        return response;
    }
}
