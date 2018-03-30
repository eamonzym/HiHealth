package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * 健康目标信息
 * 和目标记录是
 * 1 对 多
 * 健康目标信息
 * 和目标类型是
 * 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Target {

    // 主键 目标编号
    @Column(unique = true)
    private int target_Id;

    // 外键 目标类型编号
    // 多 对 1
    @Column(nullable = false)
    private TargetType targetType;
    // 1 对 多
    private List<TargetLog> targetLogList = new ArrayList<TargetLog>();
    // 不为空 目标名称
    @Column(nullable = false)
    private String target_Name;

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
    private Time target_FinishTime;

    // 不为空 目标建立时间
    @Column(nullable = false)
    private Date target_CreatTime;

    public int getTarget_Id() {
        return target_Id;
    }

    public void setTarget_Id(int target_Id) {
        this.target_Id = target_Id;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public List<TargetLog> getTargetLogList() {
        return targetLogList;
    }

    public void setTargetLogList(List<TargetLog> targetLogList) {
        this.targetLogList = targetLogList;
    }

    public String getTarget_Name() {
        return target_Name;
    }

    public void setTarget_Name(String target_Name) {
        this.target_Name = target_Name;
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

    public Time getTarget_FinishTime() {
        return target_FinishTime;
    }

    public void setTarget_FinishTime(Time target_FinishTime) {
        this.target_FinishTime = target_FinishTime;
    }

    public Date getTarget_CreatTime() {
        return target_CreatTime;
    }

    public void setTarget_CreatTime(Date target_CreatTime) {
        this.target_CreatTime = target_CreatTime;
    }
}
