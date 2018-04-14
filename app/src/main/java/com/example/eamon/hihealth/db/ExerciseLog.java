package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.Date;

/**
 * 运动记录信息
 * 和运动信息是 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class ExerciseLog {

    // 主键 运动检录信息编号
    @Column(unique = true)
    private String exerciseLog_Id;

    // 外键 运动信息编号
    @Column(nullable = false)
    private Exrecise exrecise;
    // 不空 运动距离
    @Column(nullable = false)
    private double exerciseLog_Distance;

    // 不空 运动时长
    @Column(nullable = false)
    private int exerciseLog_Duration;

    // 不空 运动消耗总卡路里
    @Column(nullable = false)
    private double exerciseLog_TotalCalorie;

    // 不空 运动记录时间
    @Column(nullable = false)
    private Date exerciseLog_Time;
}