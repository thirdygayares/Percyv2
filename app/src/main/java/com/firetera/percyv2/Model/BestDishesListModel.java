package com.firetera.percyv2.Model;

public class BestDishesListModel {
    String dishName;
    String courseName;
    int eventImages;

    public BestDishesListModel(String dishName, String courseName, int eventImages) {
        this.dishName = dishName;
        this.courseName = courseName;
        this.eventImages = eventImages;
    }

    public String getDishName() {
        return dishName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int geteventImages() {
        return eventImages;
    }
}
