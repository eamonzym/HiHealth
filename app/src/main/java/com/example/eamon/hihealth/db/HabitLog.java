package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.Date;

/**
 * 健康习惯打卡记录信息
 * 和习惯信息 是多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class HabitLog {

    // 主键 习惯记录编号
    @Column(unique = true)
    private String habitLog_Id;

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

}