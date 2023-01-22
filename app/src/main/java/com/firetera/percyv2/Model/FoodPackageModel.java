package com.firetera.percyv2.Model;

public class FoodPackageModel {
    String packageName;
    String price;
    String foodNo1;
    String foodNo2;
    String foodNo3;
    String foodNo4;
    Boolean status;

    public FoodPackageModel(String packageName, String price, String foodNo1, String foodNo2, String foodNo3, String foodNo4) {
        this.packageName = packageName;
        this.price = price;
        this.foodNo1 = foodNo1;
        this.foodNo2 = foodNo2;
        this.foodNo3 = foodNo3;
        this.foodNo4 = foodNo4;
        status = false;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public String getFoodNo1() {
        return foodNo1;
    }

    public String getFoodNo2() {
        return foodNo2;
    }

    public String getFoodNo3() {
        return foodNo3;
    }

    public String getFoodNo4() {
        return foodNo4;
    }

    public Boolean getStatus() {
        return status;
    }
}