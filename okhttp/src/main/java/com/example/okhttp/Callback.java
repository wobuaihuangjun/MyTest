package com.example.okhttp;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/13 20:47
 */
public interface Callback {

    void onFailure(Call call, Exception e);

    void onResponse(Call call, Response response);

}
