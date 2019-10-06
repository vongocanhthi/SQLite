package com.anhthi.sqlite;

public class Congviec {
    int id;
    String tenCV;

    public Congviec(int id, String tenCV) {
        this.id = id;
        this.tenCV = tenCV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }
}
