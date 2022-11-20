package com.firetera.percyv2.Model;

public class DishModel {

    int Image;
    String DishName;
    int Price;


    public DishModel(int image, String dishName, int price) {
        Image = image;
        DishName = dishName;
        Price = price;
    }

    public int getImage() {
        return Image;
    }

    public String getDishName() {
        return DishName;
    }

    public int getPrice() {
        return Price;
    }

}
