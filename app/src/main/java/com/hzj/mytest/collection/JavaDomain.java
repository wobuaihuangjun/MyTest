package com.hzj.mytest.collection;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzj on 2016/3/17.
 */
public class JavaDomain {

    public static void main(String[] args) {
//
//        Integer a = null;
//
//        Integer b = 2;
//        System.out.print(b.equals(a));

        testMap();
//        System.out.print(testPerson(new Person()));

    }

    public static String testPerson(Object object) {
        return object.getClass().getName();
    }

    public static void testMap() {
        Map<String, Person> personHashMap = new HashMap<>();

        Person person = null;

        Person person2 = new Person();
        person2.setName("aaa1");
        personHashMap.put("person2", person2);

        Person person1 = new Person();
        person1.setName("bbb1");
        personHashMap.put("person1", person1);

        person = person1;
        System.out.println("person1 name:" + person.getName());

        person.setName("bbb2");
        System.out.println("person1 name:" + personHashMap.get("person1").toString());
        System.out.println("person2 name:" + personHashMap.get("person2").toString());

        person = personHashMap.get("person2");
        person.setName("aaa2");
        person1.setAge(1);

        System.out.println("person1 name:" + personHashMap.get("person1").toString());
        System.out.println("person2 name:" + personHashMap.get("person2").toString());
    }

}
