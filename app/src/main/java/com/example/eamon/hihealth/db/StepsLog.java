package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 步数记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class StepsLog {

    // 主键 步数记录编号
    @Column(unique = true)
    private int stepsLogId;

    // 不空 步数值
    @Column(nullable = false)
    private int stepsValue;

    // 不空 步数记录时间
    @Column(nullable = false)
    private Time stepsLogTime;

    public int getStepsLogId() {
        return stepsLogId;
    }

    public void setStepsLogId(int stepsLogId) {
        this.stepsLogId = stepsLogId;
    }

    public int getStepsValue() {
        return stepsValue;
    }

    public void setStepsValue(int stepsValue) {
        this.stepsValue = stepsValue;
    }

    public Time getStepsLogTime() {
        return stepsLogTime;
    }

    public void setStepsLogTime(Time stepsLogTime) {
        this.stepsLogTime = stepsLogTime;
    }
}
