package com.example.eamon.hihealth.db;

import java.io.Serializable;

/**
 * 身体数据信息
 * 和身体数据记录
 * 1 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyData implements Serializable {
    private String bodydataname;
    private String bodydatadescribe;

    public String getBodydataname() {
        return bodydataname;
    }

    public void setBodydataname(String bodydataname) {
        this.bodydataname = bodydataname;
    }

    public String getBodydatadescribe() {
        return bodydatadescribe;
    }

    public void setBodydatadescribe(String bodydatadescribe) {
        this.bodydatadescribe = bodydatadescribe;
    }
}
