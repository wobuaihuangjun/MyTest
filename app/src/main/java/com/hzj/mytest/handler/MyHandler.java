package com.hzj.mytest.handler;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 处理事件分发
 * <p/>
 * Created by hzj on 2016/11/8.
 */
public class MyHandler extends Handler {

    private static final String TAG = "MyHandler";

    /**
     * 更新间隔时间
     */
    static final int INTERVAL_UPDATE_TIME = 500;

    static final int HANDLER_DISPATCH_UPDATE = 1;

    private WeakReference<DealHandler> target;

    MyHandler(DealHandler target) {
        this.target = new WeakReference<>(target);
    }

    @Override
    public void handleMessage(Message msg) {
        if (target == null || target.get() == null) {
            return;
        }
        int what = msg.what;
        switch (what) {
            case HANDLER_DISPATCH_UPDATE:
                target.get().dispatchDataUpdate();
                break;
            default:
                break;
        }
    }
}
