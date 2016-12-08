package com.hzj.designpattern.builder;

/**
 * 食物条目的接口
 * Created by hzj on 2016/12/8.
 */

public interface Item {
    public String name();

    public Packing packing();

    public float price();
}