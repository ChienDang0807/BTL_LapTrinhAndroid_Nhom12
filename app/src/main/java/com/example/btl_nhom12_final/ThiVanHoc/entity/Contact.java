package com.example.btl_nhom12_final.ThiVanHoc.entity;

public class Contact {
    String name;
    String phone;
    String email;
    String address;
    Integer age;
    String gender;
    String note;
    String level;
    String expertise;
    Integer id;

    public Contact( Integer id, String name, String expertise, String level, String note, String gender, String address, String email, String phone, Integer age) {
        this.name = name;
        this.expertise = expertise;
        this.level = level;
        this.note = note;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.id = id;
    }

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
