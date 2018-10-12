package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康习惯打卡记录信息
 * 和习惯信息 是多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class HabitLog implements Serializable{

    private String habitstampestate;

    private Date habitstampetime;

    private Habit habit;

    private UserInfo userInfo;

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getHabitstampestate() {
        return habitstampestate;
    }

    public void setHabitstampestate(String habitstampestate) {
        this.habitstampestate = habitstampestate;
    }

    public Date getHabitstampetime() {
        return habitstampetime;
    }

    public void setHabitstampetime(Date habitstampetime) {
        this.habitstampetime = habitstampetime;
    }
}