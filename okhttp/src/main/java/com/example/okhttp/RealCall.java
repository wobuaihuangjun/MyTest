package com.example.okhttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/13 20:58
 */
public final class RealCall implements Call {

    private final OkHttpClient client;

    private final Request originalRequest;

    private boolean executed;

    RealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
        this.client = client;
        this.originalRequest = originalRequest;
    }

    @Override
    public Request request() {
        return originalRequest;
    }

    @Override
    public Response execute() throws Exception {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed");
            executed = true;
        }
        captureCallStackTrace();
        try {
            client.dispatcher().executed(this);
            Response result = getResponseWithInterceptorChain();
            if (result == null) throw new IOException("Canceled");
            return result;
        } finally {
            client.dispatcher().finished(this);
        }
    }

    private void captureCallStackTrace() {
//        Object callStackTrace = Platform.get().getStackTraceForCloseable("response.body().close()");
//        retryAndFollowUpInterceptor.setCallStackTrace(callStackTrace);
    }

    @Override
    public void enqueue(Callback responseCallback) {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed");
            executed = true;
        }
        captureCallStackTrace();
        client.dispatcher().enqueue(new AsyncCall(responseCallback));
    }

    @Override
    public void cancel() {
//        retryAndFollowUpInterceptor.cancel();
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public boolean isCanceled() {
//        return retryAndFollowUpInterceptor.isCanceled();
        return false;
    }

    final class AsyncCall implements Runnable {
        private final Callback responseCallback;

        private AsyncCall(Callback responseCallback) {
            this.responseCallback = responseCallback;
        }

        Request request() {
            return originalRequest;
        }

        RealCall get() {
            return RealCall.this;
        }

        @Override
        public void run() {
            try {
                Response response = getResponseWithInterceptorChain();

                if (responseCallback != null) {
                    responseCallback.onResponse(RealCall.this, response);
                }

            } catch (Exception e) {
                System.out.println("AsyncCall.run()-----Exception");
                if (responseCallback != null) {
                    responseCallback.onFailure(RealCall.this, e);
                }

            } finally {
                client.dispatcher().finished(this);
            }

        }
    }

    private Response getResponseWithInterceptorChain() throws Exception {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(client.interceptors());

        interceptors.add(new EndInterceptor());//如果是最后一个拦截器要有处理结果，不能再调用chain.proceed()。

        System.out.println("AsyncCall.getResponseWithInterceptorChain()，interceptors.size:" + interceptors.size());

        Interceptor.Chain chain = new RealInterceptorChain(interceptors, 0, originalRequest);
        return chain.proceed(originalRequest);
    }
}
