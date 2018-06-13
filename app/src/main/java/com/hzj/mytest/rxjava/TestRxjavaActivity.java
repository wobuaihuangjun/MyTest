package com.hzj.mytest.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hzj.mytest.R;
import com.hzj.mytest.util.ThreadUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TestRxjavaActivity extends Activity {

    private static final String TAG = "TestRxjava-hzjdemo：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.test_delay, R.id.test_just, R.id.test_takeFirst})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_delay:
                testTaskArray();
                break;
            case R.id.test_just:
                testJust();
                break;
            case R.id.test_takeFirst:
                testTakeFirst();
                break;
            default:
                break;
        }
    }

    private int count;

    private void testTakeFirst() {
        count++;
        Observable
                .just(count % 2 == 0)
                .takeFirst(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        Log.d(TAG, "takeFirst ，" + aBoolean);
                        return aBoolean;
                    }
                })
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        Log.d(TAG, "map ，" + aBoolean);
                        return aBoolean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        Log.d(TAG, "Action1 ，" + aBoolean);
                    }
                });
    }

    private void testJust() {
        Log.d(TAG, "testJust");
        Observable.just("testJust")
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String ssid) {
                        Log.i(TAG, ssid);
                        ThreadUtil.throwExceptionInMainThread();
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean result) {
                        Log.i(TAG, String.valueOf(result));
                    }
                });
    }

    private void testDelay() {
        Log.d(TAG, "testDelay");
        Observable.just(null)
                .observeOn(AndroidSchedulers.mainThread())
                .delaySubscription(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.d(TAG, "end");
                    }
                });
    }

   private void testTaskArray(){
       Log.w(TAG, "testTaskArray");
       final Task task1 = new Task(1);
       Task task2 = new Task(2);
       Task task3 = new Task(3);
       ArrayList<Task> tasks = new ArrayList<>();
       tasks.add(task1);
       tasks.add(task2);
       tasks.add(task3);

       Observable.from(tasks)
               .concatMap(new Func1<Task, Observable<Task>>() {
                   @Override
                   public Observable<Task> call(Task task) {
                       return Observable
                               .just(task.doSomeThing());
                   }
               })
               .subscribeOn(Schedulers.io())
               .delay(3000, TimeUnit.MILLISECONDS)
               .takeLast(1)
               .subscribe(new Action1<Task>() {
                   @Override
                   public void call(Task task) {
                       Log.w(TAG, "testTaskArray finish");
                       // 执行完及时任务后，延迟3000ms再执行延迟任务
                       startDelayTask();
                   }
               });
   }

    private void startDelayTask() {
        Log.w(TAG, "startDelayTask");
        Task task4 = new Task(4);
        Task task5 = new Task(5);
        Task task6 = new Task(6);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task4);
        tasks.add(task5);
        tasks.add(task6);
        Observable.from(tasks)
                .concatMap(new Func1<Task, Observable<Task>>() {
                    @Override
                    public Observable<Task> call(Task task) {
                        return Observable
                                .just(task.doSomeThing())
                                .delay(1000, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Task>() {
                    @Override
                    public void call(Task task) {
                        Log.i(TAG, "task " + task.getInteger() +" is finish:" + ThreadUtil.isMainThread());
                    }
                });
    }
}
