package com.hzj.mytest.rxjava;

import android.util.Log;

import com.hzj.mytest.util.ThreadUtil;

/**
 * Created by hzj on 2018/4/14.
 */

public class Task {

    private static final String TAG = "Task";

    private Integer integer;

    public Task(Integer integer) {
        this.integer = integer;
    }

    public Task doSomeThing() {
        task();
        return this;
    }

    private void task() {
        Log.d(TAG, "task " + integer +" is run:" + ThreadUtil.isMainThread());
    }

    public Integer getInteger() {
        return integer;
    }

    @Override
    public String toString() {
        return "Task{" +
                "integer=" + integer +
                '}';
    }
}
