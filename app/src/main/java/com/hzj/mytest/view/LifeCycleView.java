package com.hzj.mytest.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hzj.mytest.R;
import com.hzj.mytest.handler.TestHandlerActivity;

import de.greenrobot.event.EventBus;

/**
 * 自定义view，测试生命周期
 * <p>
 * Created by hzj on 2016/11/25.
 */
public class LifeCycleView extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "LifeCycleView";

    private Context context;
    private ImageView icon;

    private boolean hasNewMessage;

    public LifeCycleView(Context context) {
        super(context);
        init(context);

        Log.d(TAG, "LifeCycleView()");
    }

    public LifeCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        Log.d(TAG, "LifeCycleView( , )");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");

        EventBus.getDefault().register(this);
        initDefaultStatus();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");

        EventBus.getDefault().unregister(this);
        release();
    }

    public void onEventMainThread(ViewEvent event) {
        String action = event.getAction();
        switch (action) {
            case ViewEvent.NEW_MSG:
                setHasNewMessage(true);
                break;
            case ViewEvent.READ_MSG:
                setHasNewMessage(false);
                break;
            default:
                break;
        }
    }

    private void init(Context context) {
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.custom_view, this, true);
        icon = (ImageView) findViewById(R.id.iv_message_remind_icon);
        icon.setOnClickListener(this);
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
        if (hasNewMessage) {
            icon.setBackgroundResource(R.drawable.homepage_information);
        } else {
            icon.setBackgroundResource(R.drawable.homepage_information_null);
        }
    }

    /**
     * 初始化主界面的消息状态
     */
    public void initDefaultStatus() {
        setHasNewMessage(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message_remind_icon:
                if (hasNewMessage) {
                    EventBus.getDefault().post(new ViewEvent(ViewEvent.READ_MSG));
                    context.startActivity(new Intent(context, TestHandlerActivity.class));
                } else {
                    EventBus.getDefault().post(new ViewEvent(ViewEvent.NEW_MSG));
                }
                break;
            default:
                break;
        }
    }

    private void release() {
        context = null;
    }
}
