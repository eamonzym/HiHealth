package com.example.eamon.hihealth.db;

import java.io.Serializable;

/**
 * 运动信息
 * 和运动记录是
 * 1 对 多关系
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Exercise implements Serializable{

    private String _id;

    private String exercisename;

    private Number exercisecalorie;

    private Number exerciseeffecttime;

    private String exerciserank;

    private String exercisesuggest;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getExercisename() {
        return exercisename;
    }

    public void setExercisename(String exercisename) {
        this.exercisename = exercisename;
    }

    public Number getExercisecalorie() {
        return exercisecalorie;
    }

    public void setExercisecalorie(Number exercisecalorie) {
        this.exercisecalorie = exercisecalorie;
    }

    public Number getExerciseeffecttime() {
        return exerciseeffecttime;
    }

    public void setExerciseeffecttime(Number exerciseeffecttime) {
        this.exerciseeffecttime = exerciseeffecttime;
    }

    public String getExerciserank() {
        return exerciserank;
    }

    public void setExerciserank(String exerciserank) {
        this.exerciserank = exerciserank;
    }

    public String getExercisesuggest() {
        return exercisesuggest;
    }

    public void setExercisesuggest(String exercisesuggest) {
        this.exercisesuggest = exercisesuggest;
    }
}