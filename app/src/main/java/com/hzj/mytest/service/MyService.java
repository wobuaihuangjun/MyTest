package com.hzj.mytest.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.hzj.mytest.R;

public class MyService extends Service {

    public static final String ACTION = "com.hzj.mytest.Service";

    private static final String TAG = "MyService";

    private boolean hasShowNotification = true;

    private MyHandler handler;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopSelf(msg.what);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service oncreate");
        handler = new MyHandler();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        int type = intent.getIntExtra("type", 1);
        Log.d(TAG, "notification type is " + type);
        if (type == 0) {
            setForeground();
        } else if (type == 1) {
            createNotificationChannel();
        } else if (type == 2) {
            //创建错误的通知，抛出异常
            //android.app.RemoteServiceException: Context.startForegroundService() did not then call Service.startForeground()
            createErrorNotification();
        } else if (type == 3) {
            hasShowNotification = false;
            // stop之前没有创建通知，抛出异常
            // android.app.RemoteServiceException: Context.startForegroundService() did not then call Service.startForeground()
            // onDestroy()之前补发通知无效，会anr：java.lang.Throwable: Context.startForegroundService() did not then call Service.startForeground()
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setForeground();
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(startId);
                }
            }.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void createErrorNotification() {
        Log.d(TAG, "createErrorNotification");
        Notification notification = new Notification.Builder(this).build();
        startForeground(0, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        Log.d(TAG, "createNotificationChannel");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel("my_channel_01", "channel_name", NotificationManager.IMPORTANCE_HIGH);
        mNotificationManager.createNotificationChannel(mChannel);

        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("New Message").setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.ic_launcher)
                .setChannelId(CHANNEL_ID)
                .build();
        startForeground(1, notification);
    }

    /**
     * android系统7.0以下设置成前台Service
     * 考虑到部分7.0以上系统同样能生效，就不做系统版本限制来触发该逻辑
     */
    private synchronized void setForeground() {
        if (hasShowNotification) {
            return;
        }
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "PushService do raisePriority fore ground sdk: " + Build.VERSION.SDK_INT);
            ForegroundUtil.raisePriority(this);
        } else {
            notification = new Notification();
            Log.d(TAG, "start fore ground sdk: " + Build.VERSION.SDK_INT);
            startForeground(1, notification);
        }
        hasShowNotification = true;
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy");

        if (!hasShowNotification) {
            Log.w(TAG, "补发通知");
            setForeground();
        }
        super.onDestroy();

        Toast.makeText(this, "this service destroy", Toast.LENGTH_LONG).show();
//        stopForeground(true);
    }
}
