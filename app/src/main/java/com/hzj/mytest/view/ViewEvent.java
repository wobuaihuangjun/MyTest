package com.hzj.mytest.view;

/**
 * Created by huangzj on 2015/10/23.
 * <p/>
 * 消息中心时间消息
 */
public class ViewEvent {

    /**
     * 获取到新的记录
     */

    public static final String NEW_MSG = "com.xtc.watch.MSG_NEW";
    /**
     * 新消息已读
     */
    public static final String READ_MSG = "com.xtc.watch.READ_MSG";

    private String action;

    public ViewEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
