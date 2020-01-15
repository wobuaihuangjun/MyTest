package com.example.okhttp;

import java.io.IOException;
import java.util.List;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/15 16:16
 */
public class RealInterceptorChain implements Interceptor.Chain {

    private final List<Interceptor> interceptors;
    private final int index;
    private final Request request;

    public RealInterceptorChain(List<Interceptor> interceptors, int index, Request request) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response proceed(Request request) throws IOException {
        System.out.println("RealInterceptorChain.proceed(),index:" + index);
        if (index >= interceptors.size()) throw new AssertionError();

        // Call the next interceptor in the chain.
        RealInterceptorChain next = new RealInterceptorChain(interceptors, index + 1, request);
        Interceptor interceptor = interceptors.get(index);

        return interceptor.intercept(next);
    }

}
