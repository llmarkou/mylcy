package com.lbbs.test.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2018/4/8-10:34
 * Author: 刘兵兵
 */

public class TestBean {
    private String name;
    private int age;
    private String address;
    private String email;
    private List userList;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List getUserList() {
        if (userList == null) {
            return new ArrayList<>();
        }
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public void a() {
        b();
        c();
        d();
    }

    public void b() {
        e();
        f();
    }

    public void c() {

    }

    public void d() {

    }

    public void e() {
    }

    public void f() {
    }

}
