package com.example.btl_nhom12_final.LyThiThanhHuyen.entity;

public class User {
    private String name;
    private String phone;
    private String email;
    private String pass;
    private String dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name, String dob, String email, String phone, String pass) {
        this.name = name;
        this.dob = dob;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
    }


    public User() {
    }
}