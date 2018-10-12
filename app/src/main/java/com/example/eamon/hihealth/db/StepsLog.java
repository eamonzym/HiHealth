package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 步数记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class StepsLog implements Serializable {
    private Number stepslogvalue;

    private Date stepslogtime;

    public Number getStepslogvalue() {
        return stepslogvalue;
    }

    public void setStepslogvalue(Number stepslogvalue) {
        this.stepslogvalue = stepslogvalue;
    }

    public Date getStepslogtime() {
        return stepslogtime;
    }

    public void setStepslogtime(Date stepslogtime) {
        this.stepslogtime = stepslogtime;
    }
}