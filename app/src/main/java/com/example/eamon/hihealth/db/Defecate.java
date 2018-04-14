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
    private String defecateId;

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

}
