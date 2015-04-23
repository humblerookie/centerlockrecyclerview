package com.demo;

/**
 * Created by root on 23/4/15.
 */
public class SampleBean {
    String title;
    boolean normal=true;

    public String getTitle() {
        return title;
    }

    public SampleBean setTitle(String title) {
        this.title = title; return this;
    }

    public boolean isNormal() {
        return normal;
    }

    public SampleBean setNormal(boolean normal) {
        this.normal = normal; return this;
    }
}
