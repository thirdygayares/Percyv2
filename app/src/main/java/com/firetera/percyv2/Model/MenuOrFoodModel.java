package com.firetera.percyv2.Model;

public class MenuOrFoodModel {
    String foodName;
    String courseName;
    int image;

    public MenuOrFoodModel(String foodName, String courseName, int image) {
        this.foodName = foodName;
        this.courseName = courseName;
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getImage() {
        return image;
    }
}