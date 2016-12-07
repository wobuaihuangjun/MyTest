package com.hzj.mytest.thread;

public class Person {

    private String id;

    /**
     * 性别
     */
    private Integer value;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
