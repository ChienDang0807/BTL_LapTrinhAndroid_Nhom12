package com.example.btl_nhom12_final.DangThoChien.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    Integer id;

    String tenThucDon;

    List<Food> danhSachMonAn = new ArrayList<>();

    public Menu(){

    }

    public Menu(String tenThucDon, List<Food> danhSachMonAn) {
        this.tenThucDon = tenThucDon;
        this.danhSachMonAn = danhSachMonAn;
    }

    public Menu(String tenThucDon, List<Food> danhSachMonAn, Integer id) {
        this.tenThucDon = tenThucDon;
        this.danhSachMonAn = danhSachMonAn;
        this.id = id;
    }

    public String getTenThucDon() {
        return tenThucDon;
    }

    public void setTenThucDon(String tenThucDon) {
        this.tenThucDon = tenThucDon;
    }



    public List<Food> getDanhSachMonAn() {
        return danhSachMonAn;
    }

    public void setDanhSachMonAn(List<Food> danhSachMonAn) {
        this.danhSachMonAn = danhSachMonAn;
    }

    public int getTotalCalories() {
        int total = 0;
        for (Food food : danhSachMonAn) {
            total += food.getCalories();
        }
        return total;
    }

    public void addFood(Food food) {
        this.danhSachMonAn.add(food);
    }
}
