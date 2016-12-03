package com.hzj.mytest.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hzj.mytest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestViewActivity extends Activity {

    private static final String TAG = "TestView-hzjdemo：";
    @Bind(R.id.owloading)
    OWLoadingView owLoadingView;
    @Bind(R.id.start_anim)
    Button startAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ButterKnife.bind(this);

        owLoadingView = (OWLoadingView) findViewById(R.id.owloading);
    }

    @OnClick(R.id.start_anim)
    public void onClick() {
        if (startAnim.getText().equals("中止")) {
            owLoadingView.stopAnim();
            startAnim.setText("开始");
        } else {
            owLoadingView.startAnim();
            startAnim.setText("中止");
        }
    }
}
