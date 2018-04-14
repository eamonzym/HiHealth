package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动信息
 * 和运动记录是
 * 1 对 多关系
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Exrecise {

    // 主键 运动信息编号
    @Column(unique = true)
    private String exrecise_Id;

    //1 对 多
    private List<ExerciseLog> exerciseLogList = new ArrayList<ExerciseLog>();

    // 不空 运动信息名称
    @Column(nullable = false)
    private String exrecise_Name;

    // 不空 运动最低消耗卡路里
    @Column(nullable = false)
    private double exrecise_Calorie;

    // 不空 运动有效最低时长
    @Column(nullable = false)
    private int exercise_EffectTime;

    // 不空 运动等级
    @Column(nullable = false)
    private String exercise_Rank;

    // 不空 运动建议
    @Column(nullable = false)
    private String exercise_Suggest;
}