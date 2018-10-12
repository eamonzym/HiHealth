package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 饮食记录信息
 * 和食物信息
 * 是 多 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class DietLog implements Serializable{
    private Number dietquantity;
    private Number dietcalorie;
    private Date dietlogtime;
    private String diettype;
    private Food foodid;
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Food getFood() {
        return foodid;
    }

    public void setFood(Food food) {
        this.foodid = food;
    }

    public Number getDietquantity() {
        return dietquantity;
    }

    public void setDietquantity(Number dietquantity) {
        this.dietquantity = dietquantity;
    }

    public Number getDietcalorie() {
        return dietcalorie;
    }

    public void setDietcalorie(Number dietcalorie) {
        this.dietcalorie = dietcalorie;
    }

    public Date getDietlogtime() {
        return dietlogtime;
    }

    public void setDietlogtime(Date dietlogtime) {
        this.dietlogtime = dietlogtime;
    }

    public String getDiettype() {
        return diettype;
    }

    public void setDiettype(String diettype) {
        this.diettype = diettype;
    }
}
