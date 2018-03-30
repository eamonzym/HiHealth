package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动信息
 * 和运动记录是
 * 1 对 多关系
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Exrecise {

    // 主键 运动信息编号
    @Column(unique = true)
    private int exrecise_Id;

    //1 对 多
    private List<ExerciseLog> exerciseLogList = new ArrayList<ExerciseLog>();

    // 不空 运动信息名称
    @Column(nullable = false)
    private String exrecise_Name;

    // 不空 运动最低消耗卡路里
    @Column(nullable = false)
    private double exrecise_Calorie;

    // 不空 运动有效最低时长
    @Column(nullable = false)
    private int exercise_EffectTime;

    // 不空 运动等级
    @Column(nullable = false)
    private String exercise_Rank;

    // 不空 运动建议
    @Column(nullable = false)
    private String exercise_Suggest;

    public int getExrecise_Id() {
        return exrecise_Id;
    }

    public void setExrecise_Id(int exrecise_Id) {
        this.exrecise_Id = exrecise_Id;
    }

    public List<ExerciseLog> getExerciseLogList() {
        return exerciseLogList;
    }

    public void setExerciseLogList(List<ExerciseLog> exerciseLogList) {
        this.exerciseLogList = exerciseLogList;
    }

    public String getExrecise_Name() {
        return exrecise_Name;
    }

    public void setExrecise_Name(String exrecise_Name) {
        this.exrecise_Name = exrecise_Name;
    }

    public double getExrecise_Calorie() {
        return exrecise_Calorie;
    }

    public void setExrecise_Calorie(double exrecise_Calorie) {
        this.exrecise_Calorie = exrecise_Calorie;
    }

    public int getExercise_EffectTime() {
        return exercise_EffectTime;
    }

    public void setExercise_EffectTime(int exercise_EffectTime) {
        this.exercise_EffectTime = exercise_EffectTime;
    }

    public String getExercise_Rank() {
        return exercise_Rank;
    }

    public void setExercise_Rank(String exercise_Rank) {
        this.exercise_Rank = exercise_Rank;
    }

    public String getExercise_Suggest() {
        return exercise_Suggest;
    }

    public void setExercise_Suggest(String exercise_Suggest) {
        this.exercise_Suggest = exercise_Suggest;
    }
}
