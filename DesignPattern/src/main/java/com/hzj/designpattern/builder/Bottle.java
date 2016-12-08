package com.hzj.designpattern.builder;

/**实现 Packing 接口的实体类
 * Created by hzj on 2016/12/8.
 */
public class Bottle implements Packing {

    @Override
    public String pack() {
        return "Bottle";
    }
}
