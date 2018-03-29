package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 饮食记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class DietLog {

    // 主键 饮食记录编号
    @Column(unique = true)
    private int dietLogId;

    // 不空 饮食数量
    @Column(nullable = false)
    private int dietQuantity;

    // 不空 摄入热量值
    @Column(nullable =false)
    private double dietCalorie;

    // 不空 饮食记录时间
    @Column(nullable = false)
    private Time dietLogTime;

    public int getDietLogId() {
        return dietLogId;
    }

    public void setDietLogId(int dietLogId) {
        this.dietLogId = dietLogId;
    }

    public int getDietQuantity() {
        return dietQuantity;
    }

    public void setDietQuantity(int dietQuantity) {
        this.dietQuantity = dietQuantity;
    }

    public double getDietCalorie() {
        return dietCalorie;
    }

    public void setDietCalorie(double dietCalorie) {
        this.dietCalorie = dietCalorie;
    }

    public Time getDietLogTime() {
        return dietLogTime;
    }

    public void setDietLogTime(Time dietLogTime) {
        this.dietLogTime = dietLogTime;
    }
}
