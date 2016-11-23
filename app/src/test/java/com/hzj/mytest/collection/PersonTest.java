package com.hzj.mytest.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonTest {

    @Test
    public void testList() throws Exception {
        List<String> list = new ArrayList<>();

        String className = list.remove(0);
        System.out.println("className = " + className);

        list.add("a");
        putList(list);
    }

    private void putList(List<String> list) {

        if (!list.contains("a")) {
            list.add("a");
        }

        for (String str : list) {
            System.out.println("Value = " + str);
        }
    }

    @Test
    public void testObject() throws Exception {
        System.out.print(testPerson(new Person()));
    }

    private String testPerson(Object object) {
        return object.getClass().getSimpleName();
    }

    @Test
    public void testMap() throws Exception {
        Map<String, String> updateRequest = new HashMap<>();
        updateRequest.put("a", "aaa");

        putMap(updateRequest);
    }

    private void putMap(Map<String, String> updateRequest) {
        updateRequest.put("a", "aaa");
        updateRequest.put("b", "bbb");
        updateRequest.put("b", "ccc");

        for (String key : updateRequest.keySet()) {
            System.out.println("Key = " + key);
        }

        System.out.println("遍历map中的值");
        for (String value : updateRequest.values()) {
            System.out.println("Value = " + value);
        }
    }

}