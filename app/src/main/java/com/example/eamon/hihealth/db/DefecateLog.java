package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 排便记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class DefecateLog {

    // 主键 排便记录信息编号
    @Column(unique = true)
    private int defecateLogId;

    // 不空 排便感觉描述
    @Column(nullable = false)
    private String defecateFeel;

    // 不空 排便时长
    @Column(nullable = false)
    private int defecateDuration;

    // 不空 排便记录时间
    @Column(nullable = false)
    private Time defecateLogTime;

    // 不空 排便备注
    @Column(nullable = false)
    private String defecateRemark;

    public int getDefecateLogId() {
        return defecateLogId;
    }

    public void setDefecateLogId(int defecateLogId) {
        this.defecateLogId = defecateLogId;
    }

    public String getDefecateFeel() {
        return defecateFeel;
    }

    public void setDefecateFeel(String defecateFeel) {
        this.defecateFeel = defecateFeel;
    }

    public int getDefecateDuration() {
        return defecateDuration;
    }

    public void setDefecateDuration(int defecateDuration) {
        this.defecateDuration = defecateDuration;
    }

    public Time getDefecateLogTime() {
        return defecateLogTime;
    }

    public void setDefecateLogTime(Time defecateLogTime) {
        this.defecateLogTime = defecateLogTime;
    }

    public String getDefecateRemark() {
        return defecateRemark;
    }

    public void setDefecateRemark(String defecateRemark) {
        this.defecateRemark = defecateRemark;
    }
}
