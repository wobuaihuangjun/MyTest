package com.hzj.mytest.service;

import android.app.Notification;
import android.app.Service;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 捕获系统AMS
 * <p>
 * 解决问题：启动前台Service，不弹通知栏的黑科技，原理是捕获不弹通知栏导致系统AMS指定APP抛出的异常
 * <p>
 * 参考：https://github.com/easoll/RaisePriorityHack
 * <p>
 * Hook Handle 原理的关键
 * /**
 * <p>
 * Handle.java
 * <p>
 * public void dispatchMessage(Message msg) {
 * if (msg.callback != null) {
 * handleCallback(msg);
 * } else {
 * if (mCallback != null) {
 * if (mCallback.handleMessage(msg)) {
 * return;
 * }
 * }
 * handleMessage(msg);
 * }
 * }
 * <p>
 * 外部hook这个handle，然后set一个外部自己的callback
 * 单独处理 SCHEDULE_CRASH  类型的消息，并返回true，则发现不会往下执行 handleMessage(msg)
 * 其他类型消息，callback 返回false，则继续执行 handleMessage(msg) ，则不影响其他消息的正常执行
 * <p>
 * Created by ZengJingFang on 2018/5/23.
 */

public class ForegroundUtil {

    private static final String TAG = "ForegroundUtil";

    private static boolean mHasHookH = false;

    private static int mScheduleCrashMsgWhat = 134;

    private static final int NOTIFICATION_ID = 1;

    private static volatile boolean isLock = false;

    public static boolean isLock() {
        return isLock;
    }

    public static void setLock(boolean isLock) {
        ForegroundUtil.isLock = isLock;
    }

    public static void raisePriority(Service service) {
        hookH();

        // import android.support.v4.app.NotificationCompat; 注意导包
        Notification notification = new NotificationCompat.Builder(service).
                setContentTitle("小天才").
                setSubText("Im System").
//                setCustomContentView(remoteViews).
        build();

        service.startForeground(NOTIFICATION_ID, notification);
        setLock(false);
    }

    private static void hookH() {
        if (mHasHookH) {
            return;
        }

        mHasHookH = true;

        try {
            try {
                Class hClass = Class.forName("android.app.ActivityThread$H");
                Field scheduleCrashField = hClass.getDeclaredField("SCHEDULE_CRASH");
                mScheduleCrashMsgWhat = (Integer) scheduleCrashField.get(null);
                Log.i(TAG, "get mScheduleCrashMsgWhat success");
            } catch (Exception e) {
                Log.e(TAG, "get mScheduleCrashMsgWhat failed", e);

            }

            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg == null) {
                        return false;
                    }
                    if (msg.what == mScheduleCrashMsgWhat) {
                        Log.w(TAG, new Throwable((String) msg.obj));
                        return true;
                    }

                    return false;
                }
            };

            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            Method currentActivityThread = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object activityThreadInstance = currentActivityThread.invoke(null);
            Handler hInstance = (Handler) mH.get(activityThreadInstance);
            Class handlerClass = Handler.class;
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(hInstance, callback);
        } catch (Exception e) {
            Log.e(TAG, "hook error: ", e);
        }
    }
}
