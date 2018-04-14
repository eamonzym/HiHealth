package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.Date;

/**
 * 排便记录信息
 * 和排便信息是多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class DefecateLog {

    // 主键 排便记录信息编号
    @Column(unique = true)
    private String defecateLog_Id;

    // 外键 排便信息编号
    @Column(nullable = false)
    private Defecate defecate;

    // 不空 排便感觉描述
    @Column(nullable = false)
    private String defecateLog_Feel;

    // 不空 排便时长
    @Column(nullable = false)
    private int defecateLog_Duration;

    // 不空 排便记录时间
    @Column(nullable = false)
    private Date defecateLog_Time;

    // 不空 排便备注
    @Column(nullable = false)
    private String defecateLog_Remark;

}
