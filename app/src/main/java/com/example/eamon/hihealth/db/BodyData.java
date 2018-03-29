package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

/**
 * 身体数据信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyData {

    // 主键 身体数据编号
    @Column(unique = true)
    private int bodyDataId;

    // 不空 身体数据名称
    @Column(nullable = false)
    private String bodyDataName;

    // 不空 身体数据信息描述
    @Column(nullable = false)
    private String bodyDataDescribe;

    public int getBodyDataId() {
        return bodyDataId;
    }

    public void setBodyDataId(int bodyDataId) {
        this.bodyDataId = bodyDataId;
    }

    public String getBodyDataName() {
        return bodyDataName;
    }

    public void setBodyDataName(String bodyDataName) {
        this.bodyDataName = bodyDataName;
    }

    public String getBodyDataDescribe() {
        return bodyDataDescribe;
    }

    public void setBodyDataDescribe(String bodyDataDescribe) {
        this.bodyDataDescribe = bodyDataDescribe;
    }
}
