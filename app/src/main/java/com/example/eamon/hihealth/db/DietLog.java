package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.Date;
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
    private String dietLog_Id;

    // 多 对 多
    private List<Food> foodList = new ArrayList<Food>();

    // 不空 饮食数量
    @Column(nullable = false)
    private int dietLog_Quantity;

    // 不空 摄入热量值
    @Column(nullable = false)
    private double dietLog_Calorie;

    // 不空 饮食记录时间
    @Column(nullable = false)
    private Date dietLog_Time;

}
