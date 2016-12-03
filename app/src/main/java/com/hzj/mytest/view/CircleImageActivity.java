package com.hzj.mytest.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.hzj.mytest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleImageActivity extends Activity {

    private static final String TAG = "CircleImage-hzjdemo：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_image);
    }
}
