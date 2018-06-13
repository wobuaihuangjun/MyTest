package com.hzj.mytest.screensize;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

import com.hzj.mytest.R;
import com.hzj.mytest.util.ScreenUtil;

public class TestScreenAdapterActivity extends Activity {

    private static final String TAG = "TestScreenAdapter：";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapter);

        textView = (TextView) findViewById(R.id.screen_data);

        Log.i(TAG, "getPxHeight: " + ScreenUtil.getPxHeight(this));
        Log.i(TAG, "getPxWidth: " + ScreenUtil.getPxWidth(this));
        Log.i(TAG, "getScreenDensity: " + ScreenUtil.getScreenDensity(this));

        Display mDisplay = getWindowManager().getDefaultDisplay();

        textView.setText("Screen Ratio: ["
                + ScreenUtil.getPxWidth(this) + "x" + ScreenUtil.getPxHeight(this) + "]"
                + "\n\n" + "Screen Display: " + mDisplay.toString());

        ScreenUtil.getScreenDensity_ByWindowManager(this);
        ScreenUtil.getScreenDensity_ByResources(this);
    }

}
