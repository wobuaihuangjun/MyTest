package com.hzj.mytest.collection;

/**
 * Created by huangzj on 2016/3/17.
 */
public class JavaDomain {

    public static void main(String[] args) {

        System.out.print(testPerson(new Person()));

    }

    public static String testPerson(Object object) {
        return object.getClass().getName();
    }

}
