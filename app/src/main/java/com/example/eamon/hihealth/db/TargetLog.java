package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 健康目标记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class TargetLog {

    // 主键 目标记录编号
    @Column(unique = true)
    private int targetLogId;

    // 不空 当前体重
    @Column(nullable = false)
    private double currentWeight;

    // 不空 健康目标记录时间
    @Column(nullable = false)
    private Time targetLogTime;

    public int getTargetLogId() {
        return targetLogId;
    }

    public void setTargetLogId(int targetLogId) {
        this.targetLogId = targetLogId;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Time getTargetLogTime() {
        return targetLogTime;
    }

    public void setTargetLogTime(Time targetLogTime) {
        this.targetLogTime = targetLogTime;
    }
}
