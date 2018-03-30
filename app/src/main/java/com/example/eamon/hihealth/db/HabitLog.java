package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;

/**
 * 健康习惯打卡记录信息
 * 和习惯信息 是多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class HabitLog {

    // 主键 习惯记录编号
    @Column(unique = true)
    private int habitLog_Id;

    // 外键 习惯信息编号
    // 多 对 1
    @Column(nullable = false)
    private Habit habit;

    // 不空 习惯打卡状态
    @Column(nullable = false)
    private String habitLog_StampeState;

    // 不空 习惯打卡日期
    @Column(nullable = false)
    private Date habitLog_StampeTime;

    public int getHabitLog_Id() {
        return habitLog_Id;
    }

    public void setHabitLog_Id(int habitLog_Id) {
        this.habitLog_Id = habitLog_Id;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public String getHabitLog_StampeState() {
        return habitLog_StampeState;
    }

    public void setHabitLog_StampeState(String habitLog_StampeState) {
        this.habitLog_StampeState = habitLog_StampeState;
    }

    public Date getHabitLog_StampeTime() {
        return habitLog_StampeTime;
    }

    public void setHabitLog_StampeTime(Date habitLog_StampeTime) {
        this.habitLog_StampeTime = habitLog_StampeTime;
    }
}
