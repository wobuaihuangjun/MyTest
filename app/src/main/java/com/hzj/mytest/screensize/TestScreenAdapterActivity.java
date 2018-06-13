package com.hzj.mytest.screensize;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hzj.mytest.R;
import com.hzj.mytest.util.ScreenUtil;

public class TestScreenAdapterActivity extends Activity {

    private static final String TAG = "TestScreenAdapter：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapter);

        Log.i(TAG, "getScreenHeight: " + ScreenUtil.getScreenHeight(this));
        Log.i(TAG, "getPxHeight: " + ScreenUtil.getPxHeight(this));
        Log.i(TAG, "getPxWidth: " + ScreenUtil.getPxWidth(this));
        Log.i(TAG, "getScreenDensity: " + ScreenUtil.getScreenDensity(this));

        ScreenUtil.getScreenDensity_ByWindowManager(this);
        ScreenUtil.getScreenDensity_ByResources(this);
        ScreenUtil.getDefaultScreenDensity(this);
    }

}
