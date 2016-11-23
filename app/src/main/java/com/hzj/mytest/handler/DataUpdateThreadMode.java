package com.hzj.mytest.handler;

/**
 * 数据更新回调线程
 * <p>
 * Created by hzj on 2016/11/23.
 */
public enum DataUpdateThreadMode {

    /**
     * 同一个线程
     */
    PostThread,

    /**
     * 主线程
     */
    MainThread,

    /**
     * 后台线程
     */
    BackgroundThread,

    /**
     * 单独开一个线程
     */
    Async
}
