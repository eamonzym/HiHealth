package com.example.eamon.hihealth.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 健康报告信息 实体类 HealthRebort
 * 健康报告信息和身体数据记录是1对多的关系
 * Created by eamon on 2018/3/26.
 */


public class HealthReport extends DataSupport implements Serializable {

    // 主键
    private int id;

    // 1 对 多 关系
    private List<BodyDataLog> bodyDataLogList = new ArrayList<BodyDataLog>();

    private int userinfo_id;

    // 设置为不空
    private int report_Scroe;

    // 设置为不空
    private Date report_Time;


    public List<BodyDataLog> getBodyDataLogList() {
        return bodyDataLogList;
    }

    public void setBodyDataLogList(List<BodyDataLog> bodyDataLogList) {
        this.bodyDataLogList = bodyDataLogList;
    }

    public int getReport_Scroe() {
        return report_Scroe;
    }

    public void setReport_Scroe(int report_Scroe) {
        this.report_Scroe = report_Scroe;
    }

    public Date getReport_Time() {
        return report_Time;
    }

    public void setReport_Time(Date report_Time) {
        this.report_Time = report_Time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserinfo_id() {
        return userinfo_id;
    }

    public void setUserinfo_id(UserInfo userinfo) {
        this.userinfo_id = userinfo.getId();
    }
}