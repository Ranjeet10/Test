package com.envent.bottlesup.test;

/**
 * Created by ronem on 7/5/18.
 */

public class Student {
    private String name;
    private String address;
    private String mobile;
    private String landline;

    public Student name(String name) {
        this.name = name;
        return this;
    }

    public Student address(String address) {
        this.address = address;
        return this;
    }

    public Student mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Student landline(String landline) {
        this.landline = landline;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", landline='" + landline + '\'' +
                '}';
    }
}
