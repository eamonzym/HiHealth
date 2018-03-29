package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

/**
 * 健康习惯信息
 * Created by eamon on 2018/3/27.
 */


public class Habit {

    //主键
    @Column(unique = true)
    private int habitId;

    //不为空
    @Column(nullable = false)
    private String habitName;

    //不为空
    @Column(nullable = false)
    private String habitState;

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitState() {
        return habitState;
    }

    public void setHabitState(String habitState) {
        this.habitState = habitState;
    }
}
