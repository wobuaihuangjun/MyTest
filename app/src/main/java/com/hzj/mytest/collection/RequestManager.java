package com.hzj.mytest.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzj on 2018/10/18.
 */

public class RequestManager {

    private List<String> requests = new ArrayList<>();

    public synchronized void add(String pushRequest) {
        if (requests.add(pushRequest)) {
            System.out.println("add a request");
        }
    }

    public synchronized void remove(String pushRequest) {
        if (requests.remove(pushRequest)) {
            System.out.println("remove a request");
        }
    }

    public synchronized void clear() {
        requests.clear();
    }
}
