package com.example.okhttp;

import java.io.IOException;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/16 8:44
 */
public class EndInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("EndInterceptor.intercept()");
        Request request = chain.request();
        System.out.println("EndInterceptor.intercept(), request.data:" + request.getData());

        Response.Builder responseBuilder = new Response.Builder();
        responseBuilder.code(200);
        responseBuilder.data("Test Complete");
        responseBuilder.message("success");
        return responseBuilder.build();
    }
}
