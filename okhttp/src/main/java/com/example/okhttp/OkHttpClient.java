package com.example.okhttp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/13 20:29
 */
public class OkHttpClient implements Call.Factory{

    private final Dispatcher dispatcher;
    private final List<Interceptor> interceptors;

    int timeout;

    public OkHttpClient() {
        this(new Builder());
    }

    OkHttpClient(Builder builder) {
        this.timeout = builder.timeout;
        this.dispatcher = builder.dispatcher;

        this.interceptors =  new ArrayList<>(builder.interceptors);
    }

    Dispatcher dispatcher() {
        return dispatcher;
    }

    public List<Interceptor> interceptors() {
        return interceptors;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    @Override
    public Call newCall(Request request) {
        return new RealCall(this, request, false /* for web socket */);
    }


    public static final class Builder {

        Dispatcher dispatcher;
        final List<Interceptor> interceptors = new ArrayList<>();

        int timeout;

       public Builder(){
           dispatcher = new Dispatcher();
        }

        Builder(OkHttpClient okHttpClient){
            this.timeout = okHttpClient.timeout;
            this.dispatcher = okHttpClient.dispatcher;
            this.interceptors.addAll(okHttpClient.interceptors);
        }

        public Builder timeout(long timeout, TimeUnit unit) {
            this.timeout = checkDuration("timeout", timeout, unit);
            return this;
        }

        private static int checkDuration(String name, long duration, TimeUnit unit) {
            if (duration < 0) throw new IllegalArgumentException(name + " < 0");
            if (unit == null) throw new NullPointerException("unit == null");
            long millis = unit.toMillis(duration);
            if (millis > Integer.MAX_VALUE) throw new IllegalArgumentException(name + " too large.");
            if (millis == 0 && duration > 0) throw new IllegalArgumentException(name + " too small.");
            return (int) millis;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder dispatcher(Dispatcher dispatcher) {
            if (dispatcher == null) throw new IllegalArgumentException("dispatcher == null");
            this.dispatcher = dispatcher;
            return this;
        }

        public OkHttpClient build() {
            return new OkHttpClient(this);
        }
    }
}
