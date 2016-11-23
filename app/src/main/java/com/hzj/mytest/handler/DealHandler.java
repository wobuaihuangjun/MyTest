package com.hzj.mytest.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 事件处理线程
 * <p>
 * Created by hzj on 2016/11/23.
 */
public class DealHandler {

    private static final String TAG = "DealHandler：";

    private Handler uiHandler;
    private Handler asyncHandler;
    private List<DataListener> listeners;

    private List<String> updateRequest;

    private MyHandler dataUpdateHandler;

    private static SoftReference<DealHandler> appServeImplRef;

    private DealHandler() {
        HandlerThread handlerThread = new HandlerThread("data_update_thread");
        handlerThread.start();
        uiHandler = new Handler(Looper.getMainLooper());
        asyncHandler = new Handler(handlerThread.getLooper());
        listeners = new ArrayList<>();

        updateRequest = new ArrayList<>();
        dataUpdateHandler = new MyHandler(this);
    }

    public static synchronized DealHandler getInstance() {
        if (appServeImplRef == null) {
            appServeImplRef = new SoftReference<>(new DealHandler());
        }

        DealHandler dataUpdateManager = appServeImplRef.get();
        if (dataUpdateManager == null) {
            dataUpdateManager = new DealHandler();
            appServeImplRef = new SoftReference<>(dataUpdateManager);
        }
        return dataUpdateManager;
    }


    public synchronized void register(DataListener daoListener) {
        daoListener.setUpdateThreadMode(DataUpdateThreadMode.MainThread);
        register(daoListener, DataUpdateThreadMode.MainThread);
    }

    public synchronized void register(DataListener daoListener, DataUpdateThreadMode dataUpdateThreadMode) {
        daoListener.setUpdateThreadMode(dataUpdateThreadMode);
        listeners.remove(daoListener);
        listeners.add(daoListener);
    }

    public synchronized void unRegister(DataListener daoListener) {
        listeners.remove(daoListener);
    }

    public synchronized void publish(Object data) {
        String className = (String) data;

        if (!updateRequest.contains(className)) {
            System.out.println(TAG + "publish：" + className);
            updateRequest.add(className);
        } else {
            System.out.println(TAG + "publish：" + className + " is contains");
        }

        if (updateRequest.size() == 1) {
            sendDataUpdateHandler();
        }

    }

    synchronized void dispatchDataUpdate() {
        if (updateRequest.size() > 0) {
            String className = updateRequest.remove(0);

            System.out.println(TAG + "dispatchDataUpdate：" + className);

            notifyDataChanged(className);

            sendDataUpdateHandler();
        }
    }

    private void sendDataUpdateHandler() {
        dataUpdateHandler.sendEmptyMessageDelayed(MyHandler.HANDLER_DISPATCH_UPDATE,
                MyHandler.INTERVAL_UPDATE_TIME);
    }

    private void notifyDataChanged(final String className) {
        for (final DataListener daoListener : listeners) {
            DataUpdateThreadMode dataUpdateThreadMode = daoListener.getUpdateThreadMode();
            if (dataUpdateThreadMode == DataUpdateThreadMode.MainThread) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onDataChanged(daoListener, className);
                    }
                });
            } else if (dataUpdateThreadMode == DataUpdateThreadMode.BackgroundThread) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    asyncHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onDataChanged(daoListener, className);
                        }
                    });
                } else {
                    onDataChanged(daoListener, className);
                }
            } else if (dataUpdateThreadMode == DataUpdateThreadMode.Async) {
                asyncHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onDataChanged(daoListener, className);
                    }
                });
            } else {
                // PostThread线程模式
                onDataChanged(daoListener, className);
            }
        }
    }

    private void onDataChanged(DataListener daoListener, String className) {
        daoListener.onDataChanged(className);
    }
}
