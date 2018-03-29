package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;

import java.sql.Time;

/**
 * 运动记录信息
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class ExerciseLog {

    // 主键 运动检录信息编号
    @Column(unique = true)
    private int exerciseLogId;

    // 不空 运动距离
    @Column(nullable = false)
    private double exerciseDistance;

    // 不空 运动时长
    @Column(nullable = false)
    private int exerciseDuration;

    // 不空 运动消耗总卡路里
    @Column(nullable = false)
    private double exerciseTotalCalorie;

    // 不空 运动记录时间
    @Column(nullable =false)
    private Time exerciseLogTime;

    public int getExerciseLogId() {
        return exerciseLogId;
    }

    public void setExerciseLogId(int exerciseLogId) {
        this.exerciseLogId = exerciseLogId;
    }

    public double getExerciseDistance() {
        return exerciseDistance;
    }

    public void setExerciseDistance(double exerciseDistance) {
        this.exerciseDistance = exerciseDistance;
    }

    public int getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(int exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public double getExerciseTotalCalorie() {
        return exerciseTotalCalorie;
    }

    public void setExerciseTotalCalorie(double exerciseTotalCalorie) {
        this.exerciseTotalCalorie = exerciseTotalCalorie;
    }

    public Time getExerciseLogTime() {
        return exerciseLogTime;
    }

    public void setExerciseLogTime(Time exerciseLogTime) {
        this.exerciseLogTime = exerciseLogTime;
    }
}
