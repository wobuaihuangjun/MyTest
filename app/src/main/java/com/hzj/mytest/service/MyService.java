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

    private static final String TAG = "MyService";

    public MyService() {
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
    }

    int lastType;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        int type = intent.getIntExtra("type", 1);
        Log.d(TAG, "notification type is " + type );
        if (type == 0) {
            if (lastType == 2){
                createNotificationChannel();
            }
            stopSelf(startId);
        }else if (type == 1) {
            createNotificationChannel();
        } else if (type == 2) {
//            createErrorNotification();
        } else if (type == 3) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createNotificationChannel();
                }
            }.start();
        }
        lastType = type;
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        Toast.makeText(this, "this service destroy", Toast.LENGTH_LONG).show();
        stopForeground(true);
    }
}
