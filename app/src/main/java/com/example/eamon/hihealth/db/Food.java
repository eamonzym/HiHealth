package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

/**
 * 食物信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Food {

    // 主键 食物编号
    @Column(unique = true)
    private int foodId;

    // 不为空 食物名称
    @Column(nullable = false)
    private String foodName;

    // 不为空 食物热量
    @Column(nullable = false)
    private double foodCalorie;

    // 不为空 食物类型
    @Column(nullable = false)
    private String foodType;

    // 不为空 食用建议
    @Column(nullable = false)
    private String foodSuggest;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(double foodCalorie) {
        this.foodCalorie = foodCalorie;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodSuggest() {
        return foodSuggest;
    }

    public void setFoodSuggest(String foodSuggest) {
        this.foodSuggest = foodSuggest;
    }
}
