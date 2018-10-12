package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康目标记录信息
 * 和健康目标信息
 * 是 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class TargetLog implements Serializable{

    private Number currentweight;

    private Date targetlogtime;

    public String targetid;

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }

    public Number getCurrentweight() {
        return currentweight;
    }

    public void setCurrentweight(Number currentweight) {
        this.currentweight = currentweight;
    }

    public Date getTargetlogtime() {
        return targetlogtime;
    }

    public void setTargetlogtime(Date targetlogtime) {
        this.targetlogtime = targetlogtime;
    }
}