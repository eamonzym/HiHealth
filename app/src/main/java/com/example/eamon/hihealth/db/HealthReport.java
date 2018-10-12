package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康报告信息 实体类 HealthRebort
 * 健康报告信息和身体数据记录是1对多的关系
 * Created by eamon on 2018/3/26.
 */


public class HealthReport implements Serializable {
    private Number reportscroe;

    private Date reporttime;

    public Number getReportscroe() {
        return reportscroe;
    }

    public void setReportscroe(Number reportscroe) {
        this.reportscroe = reportscroe;
    }

    public Date getReporttime() {
        return reporttime;
    }

    public void setReporttime(Date reporttime) {
        this.reporttime = reporttime;
    }
}