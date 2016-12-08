package com.hzj.designpattern.builder;

/**
 * 扩展了 Burger 的实体类。
 * Created by hzj on 2016/12/8.
 */

public class VegBurger extends Burger {

    @Override
    public float price() {
        return 25.0f;
    }

    @Override
    public String name() {
        return "Veg Burger";
    }
}
