package com.hzj.mytest.json;

import com.hzj.mytest.util.JSONUtil;

/**
 * Created by huangzj on 2016/3/17.
 */
public class JavaDomain {

    public static void main(String[] args) {

        People people1 = new People();
        people1.setAge(1);
        people1.setName("张三");

        People people2 = JSONUtil.fromJSON(JSONUtil.toJSON(people1), People.class);

        people2.setAge(2);

        People people3 = JSONUtil.fromJSON(JSONUtil.toJSON(people1), People.class);

        System.out.println(people2.toString());
        System.out.println(people3.toString());
    }


}
