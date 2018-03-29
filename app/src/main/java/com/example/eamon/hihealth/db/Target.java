package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 健康目标信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Target {

    // 主键 目标编号
    @Column(unique = true)
    private int targetId;

    // 不为空 目标名称
    @Column(nullable = false)
    private String targetName;

    // 不为空 初始体重
    @Column(nullable = false)
    private double initialWeight;

    // 不为空 目标体重
    @Column(nullable = false)
    private double targetWeight;

    // 不为空 预计每日减重
    @Column(nullable = false)
    private double dayLossWeight;

    // 不为空 预计目标完成时间
    @Column(nullable = false)
    private Time targetFinishTime;

    // 不为空 目标建立时间
    @Column(nullable = false)
    private Time targetCreatTime;

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public double getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(double initialWeight) {
        this.initialWeight = initialWeight;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public double getDayLossWeight() {
        return dayLossWeight;
    }

    public void setDayLossWeight(double dayLossWeight) {
        this.dayLossWeight = dayLossWeight;
    }

    public Time getTargetFinishTime() {
        return targetFinishTime;
    }

    public void setTargetFinishTime(Time targetFinishTime) {
        this.targetFinishTime = targetFinishTime;
    }

    public Time getTargetCreatTime() {
        return targetCreatTime;
    }

    public void setTargetCreatTime(Time targetCreatTime) {
        this.targetCreatTime = targetCreatTime;
    }
}
