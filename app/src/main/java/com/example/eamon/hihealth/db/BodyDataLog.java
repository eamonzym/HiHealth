package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;

/**
 * 身体数据记录信息
 * 身体数据记录信息和身体数据信息是
 *  多 对 1 的关系
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyDataLog {

    // 主键 身体数据记录编号
    @Column(unique = true)
    private int bodyDataLog_Id;

    // 外键 身体数据信息编号
    // 多 对 1
    @Column(nullable = false)
    private BodyData bodyData;

    // 外键 健康报告编号
    // 多 对 1
    @Column(nullable = false)
    private HealthReport healthReport;
    // 不空 身体数据值
    @Column(nullable = false)
    private String bodyDataLog_Value;

    // 不空 身体数据记录时间
    @Column(nullable = false)
    private Date bodyDataLog_Time;

    public int getBodyDataLog_Id() {
        return bodyDataLog_Id;
    }

    public void setBodyDataLog_Id(int bodyDataLog_Id) {
        this.bodyDataLog_Id = bodyDataLog_Id;
    }

    public BodyData getBodyData() {
        return bodyData;
    }

    public void setBodyData(BodyData bodyData) {
        this.bodyData = bodyData;
    }

    public HealthReport getHealthReport() {
        return healthReport;
    }

    public void setHealthReport(HealthReport healthReport) {
        this.healthReport = healthReport;
    }

    public String getBodyDataLog_Value() {
        return bodyDataLog_Value;
    }

    public void setBodyDataLog_Value(String bodyDataLog_Value) {
        this.bodyDataLog_Value = bodyDataLog_Value;
    }

    public Date getBodyDataLog_Time() {
        return bodyDataLog_Time;
    }

    public void setBodyDataLog_Time(Date bodyDataLog_Time) {
        this.bodyDataLog_Time = bodyDataLog_Time;
    }
}
