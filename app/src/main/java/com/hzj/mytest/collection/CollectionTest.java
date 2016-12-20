package com.hzj.mytest.collection;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzj on 2016/12/19.
 */
public class CollectionTest {

    private static final String TAG = "CollectionTest";

    static void testSparseArray() {
        SparseArray<List<Person>> sparseArray = new SparseArray<>();

        List<Person> list1 = new ArrayList<>();
        list1.add(new Person());
        sparseArray.put(1, list1);

        List<Person> list2 = sparseArray.get(2);

        if (list2 == null) {
            Log.d(TAG, "list2 == null");
        } else {
            Log.d(TAG, "list2 = " + list2.toString());
        }

        List<Person> list3 = sparseArray.get(1);
        list3.add(new Person());
        list3.add(new Person());

        Log.d(TAG, "list size = " + sparseArray.get(1).size());
    }

}
