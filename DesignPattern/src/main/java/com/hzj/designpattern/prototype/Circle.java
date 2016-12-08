package com.hzj.designpattern.prototype;

/**
 * 扩展了抽象类的实体类
 * Created by hzj on 2016/12/8.
 */

public class Circle extends Shape {

    public Circle() {
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
