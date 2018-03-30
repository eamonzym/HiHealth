package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * 饮食记录信息
 * 和食物信息
 * 是 多 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class DietLog {

    // 主键 饮食记录编号
    @Column(unique = true)
    private int dietLog_Id;

    // 多 对 多
    private List<Food> foodList = new ArrayList<Food>();

    // 不空 饮食数量
    @Column(nullable = false)
    private int dietLog_Quantity;

    // 不空 摄入热量值
    @Column(nullable =false)
    private double dietLog_Calorie;

    // 不空 饮食记录时间
    @Column(nullable = false)
    private Date dietLog_Time;

    public int getDietLog_Id() {
        return dietLog_Id;
    }

    public void setDietLog_Id(int dietLog_Id) {
        this.dietLog_Id = dietLog_Id;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public int getDietLog_Quantity() {
        return dietLog_Quantity;
    }

    public void setDietLog_Quantity(int dietLog_Quantity) {
        this.dietLog_Quantity = dietLog_Quantity;
    }

    public double getDietLog_Calorie() {
        return dietLog_Calorie;
    }

    public void setDietLog_Calorie(double dietLog_Calorie) {
        this.dietLog_Calorie = dietLog_Calorie;
    }

    public Date getDietLog_Time() {
        return dietLog_Time;
    }

    public void setDietLog_Time(Date dietLog_Time) {
        this.dietLog_Time = dietLog_Time;
    }
}
