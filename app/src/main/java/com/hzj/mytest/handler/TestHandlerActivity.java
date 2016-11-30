package com.hzj.mytest.handler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzj.mytest.R;

public class TestHandlerActivity extends Activity {

    private static final String TAG = "TestHandlerActivity-hzjdemo：";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        textView = (TextView) findViewById(R.id.text_view);

        DealHandler.getInstance().register(listener);

        Button test = (Button) findViewById(R.id.btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    DataListener listener = new DataListener() {

        @Override
        public void onDataChanged(String className) {
            System.out.println(TAG + className);
            textView.setText(className);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DealHandler.getInstance().unRegister(listener);
    }

    private int count = 1;

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 4; i++) {
                    DealHandler.getInstance().publish(count + "" + i);
                }
                count++;
            }
        }).start();
    }

}
