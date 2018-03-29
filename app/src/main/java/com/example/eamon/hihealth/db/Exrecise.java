package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

/**
 * 运动信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Exrecise {

    // 主键 运动信息编号
    @Column(unique = true)
    private int exreciseId;

    // 不空 运动信息名称
    @Column(nullable = false)
    private String exreciseName;

    // 不空 运动最低消耗卡路里
    @Column(nullable = false)
    private double exreciseCalorie;

    // 不空 运动有效最低时长
    @Column(nullable = false)
    private int exerciseEffectTime;

    // 不空 运动等级
    @Column(nullable = false)
    private String exerciseRank;

    // 不空 运动建议
    @Column(nullable = false)
    private String exerciseSuggest;

    public int getExreciseId() {
        return exreciseId;
    }

    public void setExreciseId(int exreciseId) {
        this.exreciseId = exreciseId;
    }

    public String getExreciseName() {
        return exreciseName;
    }

    public void setExreciseName(String exreciseName) {
        this.exreciseName = exreciseName;
    }

    public double getExreciseCalorie() {
        return exreciseCalorie;
    }

    public void setExreciseCalorie(double exreciseCalorie) {
        this.exreciseCalorie = exreciseCalorie;
    }

    public int getExerciseEffectTime() {
        return exerciseEffectTime;
    }

    public void setExerciseEffectTime(int exerciseEffectTime) {
        this.exerciseEffectTime = exerciseEffectTime;
    }

    public String getExerciseRank() {
        return exerciseRank;
    }

    public void setExerciseRank(String exerciseRank) {
        this.exerciseRank = exerciseRank;
    }

    public String getExerciseSuggest() {
        return exerciseSuggest;
    }

    public void setExerciseSuggest(String exerciseSuggest) {
        this.exerciseSuggest = exerciseSuggest;
    }
}
