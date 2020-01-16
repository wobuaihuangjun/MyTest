package com.example.okhttp;

import java.io.IOException;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/15 16:40
 */
public class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("ErrorInterceptor.intercept()");
        Request request = chain.request();
        Response response = chain.proceed(request);
        System.out.println("ErrorInterceptor.intercept(), response.getData :" + response.getData());

        if (!response.isSuccessful()) {
            Response.Builder responseBuilder = new Response.Builder();
            responseBuilder.code(400);
            responseBuilder.data("Test Error");
            return responseBuilder.build();
        }

        return response;
    }
}
