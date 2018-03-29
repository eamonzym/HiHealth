package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 健康习惯打卡记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class HabitLog {

    // 主键 习惯记录编号
    @Column(unique = true)
    private int habitLogId;

    // 不空 习惯打卡状态
    @Column(nullable = false)
    private String habitStampeState;

    // 不空 习惯打卡日期
    @Column(nullable = false)
    private Time habitStampeTime;

    public int getHabitLogId() {
        return habitLogId;
    }

    public void setHabitLogId(int habitLogId) {
        this.habitLogId = habitLogId;
    }

    public String getHabitStampeState() {
        return habitStampeState;
    }

    public void setHabitStampeState(String habitStampeState) {
        this.habitStampeState = habitStampeState;
    }

    public Time getHabitStampeTime() {
        return habitStampeTime;
    }

    public void setHabitStampeTime(Time habitStampeTime) {
        this.habitStampeTime = habitStampeTime;
    }
}
