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
    private String food_Id;

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

}
