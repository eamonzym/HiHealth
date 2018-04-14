package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 身体数据信息
 * 和身体数据记录
 * 1 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class BodyData extends DataSupport implements Serializable {

    // 主键 数据表id
    private int id;

    // 1 对 多 身体数据记录
    private List<BodyDataLog> bodyDataLogList = new ArrayList<BodyDataLog>();

    // 外键用户信息

    // 不空 身体数据名称
    @Column(unique = true)
    private String bodyData_Name;

    // 不空 身体数据信息描述
    private String bodyData_Describe;

    public BodyData(String bodyData_Name, String bodyData_Describe) {
        this.bodyData_Name = bodyData_Name;
        this.bodyData_Describe = bodyData_Describe;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
