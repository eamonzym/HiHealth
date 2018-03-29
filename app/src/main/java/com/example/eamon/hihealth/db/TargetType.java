package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

/**
 * 健康目标类型信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class TargetType {

    // 主键 健康目标类型编号
    @Column(unique = true)
    private int targetTypeId;

    // 不空 目标类型名称
    @Column(nullable = false)
    private String targetTypeName;

    // 不空 目标类型等级
    @Column(nullable = false)
    private String targetTypeRank;

    // 不空 目标建议
    @Column(nullable = false)
    private String targetTypeSuggest;

    public int getTargetTypeId() {
        return targetTypeId;
    }

    public void setTargetTypeId(int targetTypeId) {
        this.targetTypeId = targetTypeId;
    }

    public String getTargetTypeName() {
        return targetTypeName;
    }

    public void setTargetTypeName(String targetTypeName) {
        this.targetTypeName = targetTypeName;
    }

    public String getTargetTypeRank() {
        return targetTypeRank;
    }

    public void setTargetTypeRank(String targetTypeRank) {
        this.targetTypeRank = targetTypeRank;
    }

    public String getTargetTypeSuggest() {
        return targetTypeSuggest;
    }

    public void setTargetTypeSuggest(String targetTypeSuggest) {
        this.targetTypeSuggest = targetTypeSuggest;
    }
}
