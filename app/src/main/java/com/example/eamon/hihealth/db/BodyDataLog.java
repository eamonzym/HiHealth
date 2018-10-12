package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 身体数据记录信息
 * 身体数据记录信息和身体数据信息是
 *  多 对 1 的关系
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyDataLog implements Serializable {
    private String bodydatavalue;
    private Date bodydatalogtime;

    public String getBodydatavalue() {
        return bodydatavalue;
    }

    public void setBodydatavalue(String bodydatavalue) {
        this.bodydatavalue = bodydatavalue;
    }

    public Date getBodydatalogtime() {
        return bodydatalogtime;
    }

    public void setBodydatalogtime(Date bodydatalogtime) {
        this.bodydatalogtime = bodydatalogtime;
    }
}
