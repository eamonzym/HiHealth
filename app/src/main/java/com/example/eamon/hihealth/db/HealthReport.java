package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 健康报告信息 实体类 HealthRebort
 * 健康报告信息和身体数据记录是1对多的关系
 * Created by eamon on 2018/3/26.
 */


public class HealthReport {

    // 设置为主键
    @Column(unique = true)
    private int rebort_Id;

    // 1 对 多 关系
    @Column(nullable = false)
    private List<BodyDataLog> bodyDataLogList = new ArrayList<BodyDataLog>();

    // 设置为不空
    @Column(nullable = false)
    private int rebort_Scroe;

    // 设置为不空
    @Column(nullable = false)
    private Date rebort_Time;

    public int getRebort_Id() {
        return rebort_Id;
    }

    public void setRebort_Id(int rebort_Id) {
        this.rebort_Id = rebort_Id;
    }

    public List<BodyDataLog> getBodyDataLogList() {
        return bodyDataLogList;
    }

    public void setBodyDataLogList(List<BodyDataLog> bodyDataLogList) {
        this.bodyDataLogList = bodyDataLogList;
    }

    public int getRebort_Scroe() {
        return rebort_Scroe;
    }

    public void setRebort_Scroe(int rebort_Scroe) {
        this.rebort_Scroe = rebort_Scroe;
    }

    public Date getRebort_Time() {
        return rebort_Time;
    }

    public void setRebort_Time(Date rebort_Time) {
        this.rebort_Time = rebort_Time;
    }
}
