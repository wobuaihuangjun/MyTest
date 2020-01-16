package com.example.okhttp;


/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/13 20:40
 */
public class Response {

    private final Request request;
    private final int code;
    private final Object data;
    private final String message;

    private Response(Builder builder) {
        this.request = builder.request;
        this.code = builder.code;
        this.data = builder.data;
        this.message = builder.message;
    }

    public Request getRequest() {
        return request;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public int code() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public static class Builder {
        private Request request;
        private int code;
        private Object data;
        private String message;

        public Builder() {
            code = -1;
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Response build() {
            if (code < 0) throw new IllegalStateException("code < 0: " + code);
            return new Response(this);
        }
    }
}
