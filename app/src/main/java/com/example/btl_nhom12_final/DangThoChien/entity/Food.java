package com.example.btl_nhom12_final.DangThoChien.entity;


public class Food {
    private String foodName;
    private String servingSize;
    private Integer calories;
    private Integer kj;
    private Integer id_menu;

    public Food(){
    }

    public Food(String foodName, Integer calories) {
        this.foodName = foodName;
        this.calories = calories;
    }

    public Food(String foodName, Integer calories, String servingSize) {
        this.foodName = foodName;
        this.calories = calories;
        this.servingSize = servingSize;
    }

    public Food(String foodName, String servingSize, Integer calories, Integer kj, Integer id_menu) {
        this.foodName = foodName;
        this.servingSize = servingSize;
        this.calories = calories;
        this.kj = kj;
        this.id_menu = id_menu;
    }

    public Integer getId_menu() {
        return id_menu;
    }

    public void setId_menu(Integer id_menu) {
        this.id_menu = id_menu;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getKj() {
        return kj;
    }

    public void setKj(Integer kj) {
        this.kj = kj;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }
}
