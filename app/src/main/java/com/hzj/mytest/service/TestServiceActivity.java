package com.hzj.mytest.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.hzj.mytest.MainActivity;
import com.hzj.mytest.R;
import com.hzj.mytest.thread.Person;
import com.hzj.mytest.thread.PersonCache;
import com.hzj.mytest.util.UUIDUtil;
import com.tencent.mars.xlog.Log;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestServiceActivity extends Activity {

    private static final String TAG = "hzjdemo：";

    MyHandler handler;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case R.id.stop:
                    Intent intent0 = new Intent(TestServiceActivity.this,MyService.class);
                    intent0.putExtra("type",0);
                    startForegroundService(intent0);
                    break;
                case R.id.start1:
                    Intent intent1 = new Intent(TestServiceActivity.this,MyService.class);
                    intent1.putExtra("type",1);
                    startForegroundService(intent1);
                    break;
                case R.id.start2:
                    handler.sendEmptyMessage(0);
                    Intent intent2 = new Intent(TestServiceActivity.this,MyService.class);
                    intent2.putExtra("type",2);
                    startForegroundService(intent2);
                    break;
                case R.id.start3:
                    handler.sendEmptyMessage(0);
                    Intent intent3 = new Intent(TestServiceActivity.this,MyService.class);
                    intent3.putExtra("type",3);
                    startForegroundService(intent3);
                    break;
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        handler = new MyHandler();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.stop, R.id.start1, R.id.start2, R.id.start3})
    public void onClick(View view) {
        handler.sendEmptyMessageDelayed(view.getId(), 3000);
    }


}
