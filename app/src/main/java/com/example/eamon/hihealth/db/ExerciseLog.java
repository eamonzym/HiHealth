package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Date;

/**
 * 运动记录信息
 * 和运动信息是 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class ExerciseLog {

    // 主键 运动检录信息编号
    @Column(unique = true)
    private int exerciseLog_Id;

    // 外键 运动信息编号
    @Column(nullable = false)
    private Exrecise exrecise;
    // 不空 运动距离
    @Column(nullable = false)
    private double exerciseLog_Distance;

    // 不空 运动时长
    @Column(nullable = false)
    private int exerciseLog_Duration;

    // 不空 运动消耗总卡路里
    @Column(nullable = false)
    private double exerciseLog_TotalCalorie;

    // 不空 运动记录时间
    @Column(nullable =false)
    private Date exerciseLog_Time;

    public int getExerciseLog_Id() {
        return exerciseLog_Id;
    }

    public void setExerciseLog_Id(int exerciseLog_Id) {
        this.exerciseLog_Id = exerciseLog_Id;
    }

    public Exrecise getExrecise() {
        return exrecise;
    }

    public void setExrecise(Exrecise exrecise) {
        this.exrecise = exrecise;
    }

    public double getExerciseLog_Distance() {
        return exerciseLog_Distance;
    }

    public void setExerciseLog_Distance(double exerciseLog_Distance) {
        this.exerciseLog_Distance = exerciseLog_Distance;
    }

    public int getExerciseLog_Duration() {
        return exerciseLog_Duration;
    }

    public void setExerciseLog_Duration(int exerciseLog_Duration) {
        this.exerciseLog_Duration = exerciseLog_Duration;
    }

    public double getExerciseLog_TotalCalorie() {
        return exerciseLog_TotalCalorie;
    }

    public void setExerciseLog_TotalCalorie(double exerciseLog_TotalCalorie) {
        this.exerciseLog_TotalCalorie = exerciseLog_TotalCalorie;
    }

    public Date getExerciseLog_Time() {
        return exerciseLog_Time;
    }

    public void setExerciseLog_Time(Date exerciseLog_Time) {
        this.exerciseLog_Time = exerciseLog_Time;
    }
}
