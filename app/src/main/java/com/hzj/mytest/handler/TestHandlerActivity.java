package com.hzj.mytest.handler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hzj.mytest.R;

public class TestHandlerActivity extends Activity {

    private static final String TAG = "TestHandlerActivity：";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        textView = (TextView) findViewById(R.id.text_view);

        DealHandler.getInstance().register(listener);

        test();
    }

    DataListener listener = new DataListener() {

        @Override
        public void onDataChanged(String className) {
            textView.setText(className);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DealHandler.getInstance().unRegister(listener);
    }

    private void test() {
        int count = 20;
        for (int i = 0; i < count; i++) {
            DealHandler.getInstance().publish("" + i);
        }
    }

}
