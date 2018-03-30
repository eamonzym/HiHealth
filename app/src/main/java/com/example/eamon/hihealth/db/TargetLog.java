package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;

/**
 * 健康目标记录信息
 * 和健康目标信息
 * 是 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class TargetLog {

    // 主键 目标记录编号
    @Column(unique = true)
    private int targetLog_Id;

    // 外键 目标信息标号
    // 多 对 1

    @Column(nullable = false)
    private Target target;

    // 不空 当前体重
    @Column(nullable = false)
    private double currentWeight;

    // 不空 健康目标记录时间
    @Column(nullable = false)
    private Date targetLog_Time;

    public int getTargetLog_Id() {
        return targetLog_Id;
    }

    public void setTargetLog_Id(int targetLog_Id) {
        this.targetLog_Id = targetLog_Id;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Date getTargetLog_Time() {
        return targetLog_Time;
    }

    public void setTargetLog_Time(Date targetLog_Time) {
        this.targetLog_Time = targetLog_Time;
    }
}
