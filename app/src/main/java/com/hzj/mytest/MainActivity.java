package com.hzj.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import com.hzj.mytest.handler.DataListener;
import com.hzj.mytest.handler.DataUpdateThreadMode;
import com.hzj.mytest.handler.DealHandler;
import com.hzj.mytest.handler.TestHandlerActivity;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DealHandler.getInstance().register(listener, DataUpdateThreadMode.BackgroundThread);

        startActivity(new Intent(this, TestHandlerActivity.class));
    }

    DataListener listener = new DataListener() {

        @Override
        public void onDataChanged(String className) {
            System.out.println(TAG + className);

            System.out.println(TAG + Looper.getMainLooper().getThread().getName());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DealHandler.getInstance().unRegister(listener);
    }
}
