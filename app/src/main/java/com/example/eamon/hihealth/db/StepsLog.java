package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.Date;

/**
 * 步数记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class StepsLog {

    // 主键 步数记录编号
    @Column(unique = true)
    private String stepsLog_Id;

    // 不空 步数值
    @Column(nullable = false)
    private int stepsLog_Value;

    // 不空 步数记录时间
    @Column(nullable = false)
    private Date stepsLog_Time;
}