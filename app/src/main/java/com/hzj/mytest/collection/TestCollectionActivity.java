package com.hzj.mytest.collection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzj.mytest.R;
import com.hzj.mytest.handler.DataListener;
import com.hzj.mytest.handler.DealHandler;

import java.util.Calendar;

public class TestCollectionActivity extends Activity {

    private static final String TAG = "TestHandlerActivity-hzjdemo：";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        textView = (TextView) findViewById(R.id.text_view);


        Calendar calendar = Calendar.getInstance();


        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));

        calendar.setTimeInMillis(1498538374048L);

        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));

        Button test = (Button) findViewById(R.id.btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    private void test() {
        CollectionTest.testSparseArray();
    }

}
