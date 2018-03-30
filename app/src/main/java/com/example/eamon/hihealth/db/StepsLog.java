package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;

/**
 * 步数记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class StepsLog {

    // 主键 步数记录编号
    @Column(unique = true)
    private int stepsLog_Id;

    // 不空 步数值
    @Column(nullable = false)
    private int stepsLog_Value;

    // 不空 步数记录时间
    @Column(nullable = false)
    private Date stepsLog_Time;

    public int getStepsLog_Id() {
        return stepsLog_Id;
    }

    public void setStepsLog_Id(int stepsLog_Id) {
        this.stepsLog_Id = stepsLog_Id;
    }

    public int getStepsLog_Value() {
        return stepsLog_Value;
    }

    public void setStepsLog_Value(int stepsLog_Value) {
        this.stepsLog_Value = stepsLog_Value;
    }

    public Date getStepsLog_Time() {
        return stepsLog_Time;
    }

    public void setStepsLog_Time(Date stepsLog_Time) {
        this.stepsLog_Time = stepsLog_Time;
    }
}
