package com.example.btl_nhom12_final.LyThiThanhHuyen.entity;

public class HealthIndex {
    private String id;
    private String phone;
    private String date;
    private Double weight;
    private Double height;
    private Double bmi;

    public HealthIndex(String id, String phone, String date, Double weight, Double height, Double bmi) {
        this.phone = phone;
        this.id = id;
        this.bmi = bmi;
        this.weight = weight;
        this.date = date;
        this.height = height;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HealthIndex() {
    }
}
