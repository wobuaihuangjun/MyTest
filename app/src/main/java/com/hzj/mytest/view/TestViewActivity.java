package com.hzj.mytest.view;

import android.app.Activity;
import android.os.Bundle;

import com.hzj.mytest.R;

public class TestViewActivity extends Activity {

    private static final String TAG = "TestView-hzjdemo：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

}
