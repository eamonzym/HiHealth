package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * 排便信息
 * 和排便记录
 * 是 1 对 多关系
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Defecate {

    // 主键 排便信息编号
    @Column(unique = true)
    private int defecateId;

    // 1 对 多
    private List<DefecateLog> defecateLogList = new ArrayList<DefecateLog>();

    // 不为空 排便类型
    @Column(nullable = false)
    private String defecate_Type;

    // 不为空 排便特点
    @Column(nullable = false)
    private String defecate_Peculiarity;

    // 不为空 排便等级
    @Column(nullable = false)
    private String defecate_Rank;

    public int getDefecateId() {
        return defecateId;
    }

    public void setDefecateId(int defecateId) {
        this.defecateId = defecateId;
    }

    public List<DefecateLog> getDefecateLogList() {
        return defecateLogList;
    }

    public void setDefecateLogList(List<DefecateLog> defecateLogList) {
        this.defecateLogList = defecateLogList;
    }

    public String getDefecate_Type() {
        return defecate_Type;
    }

    public void setDefecate_Type(String defecate_Type) {
        this.defecate_Type = defecate_Type;
    }

    public String getDefecate_Peculiarity() {
        return defecate_Peculiarity;
    }

    public void setDefecate_Peculiarity(String defecate_Peculiarity) {
        this.defecate_Peculiarity = defecate_Peculiarity;
    }

    public String getDefecate_Rank() {
        return defecate_Rank;
    }

    public void setDefecate_Rank(String defecate_Rank) {
        this.defecate_Rank = defecate_Rank;
    }
}
