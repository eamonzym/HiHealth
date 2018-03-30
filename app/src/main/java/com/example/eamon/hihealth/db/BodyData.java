package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * 身体数据信息
 * 和身体数据记录
 * 1 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyData {

    // 主键 身体数据编号
    @Column(unique = true)
    private int bodyData_Id;

    // 1 对 多 身体数据记录
    private List<BodyDataLog> bodyDataLogList = new ArrayList<BodyDataLog>();

    // 不空 身体数据名称
    @Column(nullable = false)
    private String bodyData_Name;

    // 不空 身体数据信息描述
    @Column(nullable = false)
    private String bodyData_Describe;

    public int getBodyData_Id() {
        return bodyData_Id;
    }

    public void setBodyData_Id(int bodyData_Id) {
        this.bodyData_Id = bodyData_Id;
    }

    public List<BodyDataLog> getBodyDataLogList() {
        return bodyDataLogList;
    }

    public void setBodyDataLogList(List<BodyDataLog> bodyDataLogList) {
        this.bodyDataLogList = bodyDataLogList;
    }

    public String getBodyData_Name() {
        return bodyData_Name;
    }

    public void setBodyData_Name(String bodyData_Name) {
        this.bodyData_Name = bodyData_Name;
    }

    public String getBodyData_Describe() {
        return bodyData_Describe;
    }

    public void setBodyData_Describe(String bodyData_Describe) {
        this.bodyData_Describe = bodyData_Describe;
    }
}
