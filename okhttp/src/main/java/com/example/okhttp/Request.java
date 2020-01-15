package com.example.okhttp;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/13 20:39
 */
public class Request {

    // 请求的超时时间，单位为ms
    private int timeout;

    // 请求的数据
    private Object data;

    public int getTimeout() {
        return timeout;
    }

    public Object getData() {
        return data;
    }

    private Request(Builder builder) {
        this.timeout = builder.timeout;
        this.data = builder.data;
    }

    public static class Builder {
        private int timeout;

        private Object data;


        public Builder() {
            this.timeout = 20*1000;
        }


        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }


        public Builder data(Object data) {
//            if (data == null) throw new IllegalStateException("data == null");
            this.data = data;
            return this;
        }

        public Request build() {
//            if (data == null) throw new IllegalStateException("data == null");
            return new Request(this);
        }
    }
}
