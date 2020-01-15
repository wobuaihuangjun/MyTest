package com.hzj.mytest.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.hzj.mytest.R;


/**
 * New Class.
 * Date: 2019/8/21.
 * Modify by zhangdongsheng on 2019/8/21.
 * Version change description.
 *
 * @author zhangdongsheng
 */
public class ForegroundService extends Service {

    private static final String TAG = "zds";
    private MyHandler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service onCreate");
        handler = new MyHandler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        int type = intent.getIntExtra("type", 1);
        Log.i(TAG, "the create notification type is " + type + "----" + (type == 1 ? "true" : "false"));
        if (type == 1) {
            createNotificationChannel();
        } else {
            createErrorNotification();
        }
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
        return super.onStartCommand(intent, flags, startId);
    }

    private void createErrorNotification() {
        Notification notification = new Notification.Builder(this,"my_channel_02").build();
        startForeground(0, notification);
    }

    private void createNotificationChannel() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知渠道的id
        String id = "my_channel_01";
        CharSequence name = "channel_name";
        String description = "渠道描述";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotificationManager.createNotificationChannel(mChannel);

        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("New Message").setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.ic_launcher)
                .setChannelId(CHANNEL_ID)
                .build();
        startForeground(notifyID, notification);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopSelf(msg.what);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "5s onDestroy");
        Toast.makeText(this, "this service destroy", Toast.LENGTH_LONG).show();
        stopForeground(true);
    }
}


