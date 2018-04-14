package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 健康目标信息
 * 和目标记录是
 * 1 对 多
 * 健康目标信息
 * 和目标类型是
 * 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Target {

    // 主键 目标编号
    @Column(unique = true)
    private String target_Id;

    // 外键 目标类型编号
    // 多 对 1
    @Column(nullable = false)
    private TargetType targetType;
    // 1 对 多
    private List<TargetLog> targetLogList = new ArrayList<TargetLog>();
    // 不为空 目标名称
    @Column(nullable = false)
    private String target_Name;

    // 不为空 初始体重
    @Column(nullable = false)
    private double initialWeight;

    // 不为空 目标体重
    @Column(nullable = false)
    private double targetWeight;

    // 不为空 预计每日减重
    @Column(nullable = false)
    private double dayLossWeight;

    // 不为空 预计目标完成时间
    @Column(nullable = false)
    private Date target_FinishTime;

    // 不为空 目标建立时间
    @Column(nullable = false)
    private Date target_CreatTime;
}