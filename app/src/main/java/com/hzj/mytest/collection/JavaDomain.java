package com.hzj.mytest.collection;

/**
 * Created by huangzj on 2016/3/17.
 */
public class JavaDomain {

    public static void main(String[] args) {

        Integer a = null;

        Integer b = 2;
        System.out.print(b.equals(a));

//        System.out.print(testPerson(new Person()));

    }

    public static String testPerson(Object object) {
        return object.getClass().getName();
    }

}
