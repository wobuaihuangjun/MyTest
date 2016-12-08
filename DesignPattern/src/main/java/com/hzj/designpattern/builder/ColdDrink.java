package com.hzj.designpattern.builder;

/**
 * 实现 Item 接口的抽象类，该类提供了默认的功能
 * Created by hzj on 2016/12/8.
 */

public abstract class ColdDrink implements Item {

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
