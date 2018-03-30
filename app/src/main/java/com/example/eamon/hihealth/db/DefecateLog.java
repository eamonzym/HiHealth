package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;

/**
 * 排便记录信息
 * 和排便信息是多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class DefecateLog {

    // 主键 排便记录信息编号
    @Column(unique = true)
    private int defecateLog_Id;

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

    public int getDefecateLog_Id() {
        return defecateLog_Id;
    }

    public void setDefecateLog_Id(int defecateLog_Id) {
        this.defecateLog_Id = defecateLog_Id;
    }

    public Defecate getDefecate() {
        return defecate;
    }

    public void setDefecate(Defecate defecate) {
        this.defecate = defecate;
    }

    public String getDefecateLog_Feel() {
        return defecateLog_Feel;
    }

    public void setDefecateLog_Feel(String defecateLog_Feel) {
        this.defecateLog_Feel = defecateLog_Feel;
    }

    public int getDefecateLog_Duration() {
        return defecateLog_Duration;
    }

    public void setDefecateLog_Duration(int defecateLog_Duration) {
        this.defecateLog_Duration = defecateLog_Duration;
    }

    public Date getDefecateLog_Time() {
        return defecateLog_Time;
    }

    public void setDefecateLog_Time(Date defecateLog_Time) {
        this.defecateLog_Time = defecateLog_Time;
    }

    public String getDefecateLog_Remark() {
        return defecateLog_Remark;
    }

    public void setDefecateLog_Remark(String defecateLog_Remark) {
        this.defecateLog_Remark = defecateLog_Remark;
    }
}
