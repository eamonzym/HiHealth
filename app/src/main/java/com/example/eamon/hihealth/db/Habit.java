package com.example.eamon.hihealth.db;

import java.io.Serializable;

/**
 * 健康习惯信息
 * 和习惯打卡是
 * 1 对 多关系
 * Created by eamon on 2018/3/27.
 */


public class Habit implements Serializable{

    private String _id;
    private String habitname;
    private String habitstate;
    private String username;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getHabitname() {
        return habitname;
    }

    public void setHabitname(String habitname) {
        this.habitname = habitname;
    }

    public String getHabitstate() {
        return habitstate;
    }

    public void setHabitstate(String habitstate) {
        this.habitstate = habitstate;
    }
}