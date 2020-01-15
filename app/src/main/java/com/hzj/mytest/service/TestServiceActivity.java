package com.hzj.mytest.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hzj.mytest.util.LogUtil;
import com.hzj.mytest.R;

import java.util.concurrent.locks.ReentrantLock;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestServiceActivity extends Activity {

    private static final String TAG = "TestServiceActivity：";

    MyHandler handler;

    private EditText inputType;

    private class MyHandler extends Handler {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.w(TAG, "click start service");
            int type = Integer.valueOf(inputType.getText().toString());

            switch (msg.what) {
                case R.id.start_by_type:

                    startService(type);

                    if (type == 0) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                stopPushService();
                            }
                        }.start();
                    }
                    break;

                case R.id.start_foreground_service_by_type:
                    if (type == 0) {
                        testThread();
                    } else {
                        testThreadLock();
                    }
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startService(int type) {
        myServiceIntent.putExtra("type", type);
        startForegroundService(myServiceIntent);
    }

    private void stopPushService() {
        stopService();
    }

    private Intent myServiceIntent;

    private void createMyServiceIntent() {
        myServiceIntent = new Intent();
        myServiceIntent.setPackage(getPackageName());
        myServiceIntent.setComponent(new ComponentName("com.hzj.mytest", MyService.class.getName()));
        myServiceIntent.setAction(MyService.ACTION);
        myServiceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    }


    private void stopService() {
        // 判断PushService是不是该应用的Service，如果不是，仅仅只解除绑定
        boolean isSuceess = stopService(myServiceIntent);
        if (isSuceess) {
            Log.i(TAG, "Stop PushService Success.");
        } else {
            Log.w(TAG, "Stop PushService Failed.");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        handler = new MyHandler();
        createMyServiceIntent();

        inputType = findViewById(R.id.service_et);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.start_by_type, R.id.start_foreground_service_by_type})
    public void onClick(View view) {
        handler.sendEmptyMessageDelayed(view.getId(), 1000);
    }

    private ReentrantLock initLock = new ReentrantLock();

    int i = 100000;

    private void testThread() {
        LogUtil.d("testThread");
        i = 100000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 100000; j++) {
                    i++;
                }
                LogUtil.d("i++ = " + i);
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 100000; j++) {
                    i--;
                }
                LogUtil.d("i-- = " + i);
            }
        }).start();
    }

    private void testThreadLock() {
        LogUtil.d("testThreadLock");
        i = 100000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                initLock.lock();

                try {
                    for (int j = 0; j < 100000; j++) {
                        i++;
                    }
                    LogUtil.d("i++ = " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    initLock.unlock();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                initLock.lock();

                try {
                    for (int j = 0; j < 100000; j++) {
                        i--;
                    }
                    LogUtil.d("i-- = " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    initLock.unlock();
                }
            }
        }).start();
    }


}
