package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

/**
 * 排便信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Defecate {

    // 主键 排便信息编号
    @Column(unique = true)
    private int defecateId;

    // 不为空 排便类型
    @Column(nullable = false)
    private String defecateType;

    // 不为空 排便特点
    @Column(nullable = false)
    private String defecatePeculiarity;

    // 不为空 排便等级
    @Column(nullable = false)
    private String defecateRank;

    public int getDefecateId() {
        return defecateId;
    }

    public void setDefecateId(int defecateId) {
        this.defecateId = defecateId;
    }

    public String getDefecateType() {
        return defecateType;
    }

    public void setDefecateType(String defecateType) {
        this.defecateType = defecateType;
    }

    public String getDefecatePeculiarity() {
        return defecatePeculiarity;
    }

    public void setDefecatePeculiarity(String defecatePeculiarity) {
        this.defecatePeculiarity = defecatePeculiarity;
    }

    public String getDefecateRank() {
        return defecateRank;
    }

    public void setDefecateRank(String defecateRank) {
        this.defecateRank = defecateRank;
    }
}
