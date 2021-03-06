package com.hzj.mytest.thread;

import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Person缓存
 * <p>
 * Created by hzj on 2016/11/24.
 */
public class PersonCache {

    private static final String TAG = "PersonCache,hzjdemo：";

    private Map<String, Person> cacheList;

    private static SoftReference<PersonCache> personCacheRef;

    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    private PersonCache() {
        cacheList = new Hashtable<>();
    }

    public void hasUpdatedData() {
        cacheList.clear();
    }

    public static synchronized PersonCache getInstance() {
        if (personCacheRef == null) {
            personCacheRef = new SoftReference<>(new PersonCache());
        }

        PersonCache personCache = personCacheRef.get();
        if (personCache == null) {
            personCache = new PersonCache();
            personCacheRef = new SoftReference<>(personCache);
        }
        return personCache;
    }

    public synchronized Person getPerson(String watchId) {
        if (TextUtils.isEmpty(watchId)) {
            return null;
        }
        Person person = cacheList.get(watchId);
        if (person != null) {
            Log.d(TAG, Thread.currentThread().getName() + " === get === " + person.toString());
        } else {
            Log.d(TAG, Thread.currentThread().getName() + " === get === " + null);
        }
        return person;
    }

    public synchronized void setPerson(Person person) {
        if (person == null) {
            return;
        }
        cacheList.put(person.getId(), person);
        Log.i(TAG, Thread.currentThread().getName() + " === set === " + person.toString());
    }

}
