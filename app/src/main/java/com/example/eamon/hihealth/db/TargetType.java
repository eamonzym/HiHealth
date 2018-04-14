package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康目标类型信息
 * 和健康目标信息
 * 是1 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class TargetType {

    // 主键 健康目标类型编号
    @Column(unique = true)
    private String targetType_Id;

    //1 对 多
    private List<Target> targetList = new ArrayList<Target>();

    // 不空 目标类型名称
    @Column(nullable = false)
    private String targetType_Name;

    // 不空 目标类型等级
    @Column(nullable = false)
    private String targetType_Rank;

    // 不空 目标建议
    @Column(nullable = false)
    private String targetType_Suggest;
}