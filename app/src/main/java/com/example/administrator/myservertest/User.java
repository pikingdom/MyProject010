package com.example.administrator.myservertest;

/**
 * Created by Administrator on 2018/8/14.
 */

public class User {
    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String sex;
}
