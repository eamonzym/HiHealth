package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康习惯信息
 * 和习惯打卡是
 * 1 对 多关系
 * Created by eamon on 2018/3/27.
 */


public class Habit {

    //主键
    @Column(unique = true)
    private int habit_Id;

    // 1 对 多
    private List<HabitLog> habitLogList = new ArrayList<HabitLog>();

    //不为空
    @Column(nullable = false)
    private String habit_Name;

    //不为空
    @Column(nullable = false)
    private String habit_State;

    public int getHabit_Id() {
        return habit_Id;
    }

    public void setHabit_Id(int habit_Id) {
        this.habit_Id = habit_Id;
    }

    public List<HabitLog> getHabitLogList() {
        return habitLogList;
    }

    public void setHabitLogList(List<HabitLog> habitLogList) {
        this.habitLogList = habitLogList;
    }

    public String getHabit_Name() {
        return habit_Name;
    }

    public void setHabit_Name(String habit_Name) {
        this.habit_Name = habit_Name;
    }

    public String getHabit_State() {
        return habit_State;
    }

    public void setHabit_State(String habit_State) {
        this.habit_State = habit_State;
    }
}
