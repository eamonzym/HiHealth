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
    private String habit_Id;

    // 1 对 多
    private List<HabitLog> habitLogList = new ArrayList<HabitLog>();

    //不为空
    @Column(nullable = false)
    private String habit_Name;

    //不为空
    @Column(nullable = false)
    private String habit_State;

}