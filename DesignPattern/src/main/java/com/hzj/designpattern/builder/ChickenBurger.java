package com.hzj.designpattern.builder;

/**
 * 扩展了 Burger的实体类。
 * Created by hzj on 2016/12/8.
 */

public class ChickenBurger extends Burger {

    @Override
    public float price() {
        return 50.5f;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }
}
