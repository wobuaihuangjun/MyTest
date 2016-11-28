package com.hzj.mytest.handler;

/**
 * 数据更新监听接口
 * <p>
 * Created by hzj on 2016/11/23.
 */
public abstract class DataListener {

    private DataUpdateThreadMode daoThreadMode = DataUpdateThreadMode.MainThread;

    public abstract void onDataChanged(String className);

    DataUpdateThreadMode getUpdateThreadMode() {
        return daoThreadMode;
    }

    void setUpdateThreadMode(DataUpdateThreadMode daoThreadMode) {
        this.daoThreadMode = daoThreadMode;
    }
}

