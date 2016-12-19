package com.hzj.mytest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hzj.mytest.R;
import com.hzj.view.menu.SquareMenuActivity;
import com.hzj.view.simple.CircleImageActivity;
import com.hzj.view.loading.OWLoadingView;

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

    @Override
    protected void onPause() {
        super.onPause();

        owLoadingView.stopAnim();
    }

    @OnClick({R.id.start_anim, R.id.circle_image, R.id.square_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_anim:
                if (startAnim.getText().equals("中止")) {
                    owLoadingView.stopAnim();
                    startAnim.setText("开始");
                } else {
                    owLoadingView.startAnim();
                    startAnim.setText("中止");
                }
                break;
            case R.id.circle_image:
                startActivity(new Intent(this, CircleImageActivity.class));
                break;
            case R.id.square_menu:
                startActivity(new Intent(this, SquareMenuActivity.class));
                break;
        }
    }
}
