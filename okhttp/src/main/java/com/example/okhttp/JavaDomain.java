package com.example.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/15 9:01
 */
public class JavaDomain {


    public static void main(String[] arg) {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.timeout(10000, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        okHttpClient.addInterceptor(new TestInterceptor());
        okHttpClient.addInterceptor(new ErrorInterceptor());

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.data("Test");
        Request request = requestBuilder.build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {
                System.out.println("-----------onFailure----------: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response){
                System.out.println("-----------onResponse----------: " + response.getData());
            }
        });


    }

}
