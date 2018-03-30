package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * 食物信息
 * 和饮食记录是
 * 多 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Food {

    // 主键 食物编号
    @Column(unique = true)
    private int food_Id;

    // 多 对 多（要创建中间表）
    private List<DietLog> dietLogList = new ArrayList<DietLog>();

    // 不为空 食物名称
    @Column(nullable = false)
    private String food_Name;

    // 不为空 食物热量
    @Column(nullable = false)
    private double food_Calorie;

    // 不为空 食物类型
    @Column(nullable = false)
    private String food_Type;

    // 不为空 食用建议
    @Column(nullable = false)
    private String food_Suggest;

    public int getFood_Id() {
        return food_Id;
    }

    public void setFood_Id(int food_Id) {
        this.food_Id = food_Id;
    }

    public List<DietLog> getDietLogList() {
        return dietLogList;
    }

    public void setDietLogList(List<DietLog> dietLogList) {
        this.dietLogList = dietLogList;
    }

    public String getFood_Name() {
        return food_Name;
    }

    public void setFood_Name(String food_Name) {
        this.food_Name = food_Name;
    }

    public double getFood_Calorie() {
        return food_Calorie;
    }

    public void setFood_Calorie(double food_Calorie) {
        this.food_Calorie = food_Calorie;
    }

    public String getFood_Type() {
        return food_Type;
    }

    public void setFood_Type(String food_Type) {
        this.food_Type = food_Type;
    }

    public String getFood_Suggest() {
        return food_Suggest;
    }

    public void setFood_Suggest(String food_Suggest) {
        this.food_Suggest = food_Suggest;
    }
}
