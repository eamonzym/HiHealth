package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 运动记录信息
 * 和运动信息是 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class ExerciseLog implements Serializable{

    private String _id;

    private Number exerciseduration;

    private Number exercisetotalcalorie;

    private Date exerciselogtime;

    private UserInfo userInfo;

    private Exercise exerciseid;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Number getExerciseduration() {
        return exerciseduration;
    }

    public void setExerciseduration(Number exerciseduration) {
        this.exerciseduration = exerciseduration;
    }

    public Number getExercisetotalcalorie() {
        return exercisetotalcalorie;
    }

    public void setExercisetotalcalorie(Number exercisetotalcalorie) {
        this.exercisetotalcalorie = exercisetotalcalorie;
    }

    public Date getExerciselogtime() {
        return exerciselogtime;
    }

    public void setExerciselogtime(Date exerciselogtime) {
        this.exerciselogtime = exerciselogtime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Exercise getExercise() {
        return exerciseid;
    }

    public void setExercise(Exercise exercise) {
        this.exerciseid = exercise;
    }
}