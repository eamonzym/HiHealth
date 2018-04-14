package com.example.eamon.hihealth.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * 身体数据记录信息
 * 身体数据记录信息和身体数据信息是
 *  多 对 1 的关系
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyDataLog extends DataSupport implements Serializable {

    // 主键
    private int id;

    private int  bodydata_id;

    private int healthReport_id;

    private int userinfo_id;

    private String bodyDataLog_Value;


    private Date bodyDataLog_Time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getBodyData() {
        return bodydata_id;
    }

    public void setBodyData(BodyData bodyData) {
        bodydata_id = bodyData.getId();
    }

    public int getHealthReport() {
        return healthReport_id;
    }

    public void setHealthReport(HealthReport healthReport) {
        this.healthReport_id = healthReport.getId();
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

    public int getUserInfo_id() {
        return userinfo_id;
}

    public void setUserInfo(UserInfo userInfo) {
        this.userinfo_id = userInfo.getId();
    }
}
