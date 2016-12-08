package com.hzj.designpattern.builder;

/**
 * 扩展了 ColdDrink 的实体类。
 * Created by hzj on 2016/12/8.
 */

public class Pepsi extends ColdDrink {

    @Override
    public float price() {
        return 35.0f;
    }

    @Override
    public String name() {
        return "Pepsi";
    }
}
