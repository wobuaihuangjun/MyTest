package com.hzj.mytest.collection;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzj on 2016/12/19.
 */
public class CollectionTest {

    private static final String TAG = "CollectionTest，";

    public static void testSparseArray() {
        SparseArray<Long> sparseArray = new SparseArray<>();

        sparseArray.put(3, 100001L);

        System.out.println(TAG + "indexOfKey = " + sparseArray.indexOfKey(3));
        System.out.println(TAG + "indexOfValue = " + sparseArray.indexOfValue(100001L));
        System.out.println(TAG + "valueAt = " + sparseArray.valueAt(sparseArray.indexOfKey(3)));

        Long long2 = sparseArray.get(2);

        if (long2 == null) {
            System.out.println(TAG + "long2 == null");
        } else {
            System.out.println(TAG + "long2 = " + long2.toString());
        }

        sparseArray.put(3,100002L);

        System.out.println(TAG + "long1 = " + sparseArray.get(3));
    }

    public static void testSparseArray1() {
        SparseArray<List<Person>> sparseArray = new SparseArray<>();

        List<Person> list1 = new ArrayList<>();
        list1.add(new Person());
        sparseArray.put(1, list1);

        List<Person> list2 = sparseArray.get(2);

        if (list2 == null) {
            System.out.println(TAG + "list2 == null");
        } else {
            System.out.println(TAG + "list2 = " + list2.toString());
        }

        List<Person> list3 = sparseArray.get(1);
        list3.add(new Person());
        list3.add(new Person());

        System.out.println(TAG + "list size = " + sparseArray.get(1).size());
    }

}
