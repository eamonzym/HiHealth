package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 健康报告信息 实体类 HealthRebort
 *
 * Created by eamon on 2018/3/26.
 */


public class HealthReport {

    // 设置为主键
    @Column(unique = true)
    private int rebortId;

    // 设置为不空
    @Column(nullable = false)
    private int rebortScroe;

    // 设置为不空
    @Column(nullable = false)
    private Time rebortTime;

    public int getRebortId() {
        return rebortId;
    }

    public void setRebortId(int rebortId) {
        this.rebortId = rebortId;
    }

    public int getRebortScroe() {
        return rebortScroe;
    }

    public void setRebortScroe(int rebortScroe) {
        this.rebortScroe = rebortScroe;
    }

    public Time getRebortTime() {
        return rebortTime;
    }

    public void setRebortTime(Time rebortTime) {
        this.rebortTime = rebortTime;
    }
}
