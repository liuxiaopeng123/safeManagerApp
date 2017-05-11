package com.xiaopeng.safemanager.bean;

/**
 * Created by liupeng on 2017/5/4.
 */

public class FoodDataBean {

    /**
     * foodName : 苹果
     * foodEnergy : 52
     * foodCount : 100
     * foodType : 水果
     * foodEatTime : 早中晚
     * foodStatus : 0
     */

    private String foodName;
    private String foodEnergy;
    private String foodCount;
    private String foodType;
    private String foodEatTime;
    private String foodStatus;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodEnergy() {
        return foodEnergy;
    }

    public void setFoodEnergy(String foodEnergy) {
        this.foodEnergy = foodEnergy;
    }

    public String getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(String foodCount) {
        this.foodCount = foodCount;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodEatTime() {
        return foodEatTime;
    }

    public void setFoodEatTime(String foodEatTime) {
        this.foodEatTime = foodEatTime;
    }

    public String getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(String foodStatus) {
        this.foodStatus = foodStatus;
    }
}
