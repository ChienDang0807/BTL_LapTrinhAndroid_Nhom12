package com.example.btl_nhom12_final.DangThoChien.entity;

public class Exercise {
    String tenBaiTap;
    String thoiGianTap;
    String moTa;


    public Exercise(String tenBaiTap, String thoiGianTap, String moTa) {
        this.tenBaiTap = tenBaiTap;
        this.thoiGianTap = thoiGianTap;
        this.moTa = moTa;
    }

    public String getTenBaiTap() {
        return tenBaiTap;
    }

    public void setTenBaiTap(String tenBaiTap) {
        this.tenBaiTap = tenBaiTap;
    }

    public String getThoiGianTap() {
        return thoiGianTap;
    }

    public void setThoiGianTap(String thoiGianTap) {
        this.thoiGianTap = thoiGianTap;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
