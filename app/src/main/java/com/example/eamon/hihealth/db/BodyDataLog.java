package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 身体数据记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyDataLog {

    // 主键 身体数据记录编号
    @Column(unique = true)
    private int bodyDataLogId;

    // 不空 身体数据值
    @Column(nullable = false)
    private String bodyDataLogValue;

    // 不空 身体数据记录时间
    @Column(nullable = false)
    private Time bodyDataLog;

    public int getBodyDataLogId() {
        return bodyDataLogId;
    }

    public void setBodyDataLogId(int bodyDataLogId) {
        this.bodyDataLogId = bodyDataLogId;
    }

    public String getBodyDataLogValue() {
        return bodyDataLogValue;
    }

    public void setBodyDataLogValue(String bodyDataLogValue) {
        this.bodyDataLogValue = bodyDataLogValue;
    }

    public Time getBodyDataLog() {
        return bodyDataLog;
    }

    public void setBodyDataLog(Time bodyDataLog) {
        this.bodyDataLog = bodyDataLog;
    }
}
